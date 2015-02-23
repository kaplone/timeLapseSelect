package timeSelect;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TSUIController implements Initializable {
	
	//@FXML
	//private ChoiceBox<String> source;
	@FXML
	private Button rep_lec_btn;
	@FXML
	private ChoiceBox<String> plage;
	@FXML
	private ChoiceBox<String> decalage;
	@FXML
	private ChoiceBox<String> debut;
	@FXML
	private DatePicker date_deb_pick;
	@FXML
	private DatePicker date_fin_pick;
	@FXML
	private Button exporter_btn;
	@FXML
	private ProgressBar pgrs_bar;
	@FXML
	private ProgressBar pgrs_bar_2;
	@FXML
	private ImageView view;
	@FXML
	private Slider slider;
	@FXML
	private Button settings_btn;
	@FXML
	private Label label;
	@FXML
	private Polygon play_btn;
	
	private ObservableList<String> plages = FXCollections.observableArrayList();
	private ObservableList<String> decalages = FXCollections.observableArrayList();
	private ObservableList<String> debuts = FXCollections.observableArrayList();
	
	private ObservableList<File> aff = FXCollections.observableArrayList();
	
	private StringProperty nom = new SimpleStringProperty();
	private IntegerProperty num = new SimpleIntegerProperty();
	
	private ObjectProperty<Image> sbi = new SimpleObjectProperty<>();
	
	private Image tmpImage;
	
	private Select sel = new Select();
	
	private Date date_deb;
	private Date date_fin;
	
	private File repLec;
	private File export;
	
	private String home;
	
	private File settings_file ;
	
	private static Settings settings = new Settings();;
	
	
	
	protected File chooseRepLec(){
		
		Stage newStage = new Stage();
		
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Répertoire de lecture");
		File selectedDir = dirChooser.showDialog(newStage);
		 if (selectedDir != null) {
			 return selectedDir;
		 }
		 else {
			 return (File) null;
		 }
		
	}
	
    protected File chooseExport(){
		
		Stage newStage = new Stage();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Enregistrer sous");
		fileChooser.getExtensionFilters().addAll(
		         new FileChooser.ExtensionFilter(".mov videos", "*.mov"));
		File selectedFile = fileChooser.showSaveDialog(newStage);
		if (selectedFile != null) {
			 return selectedFile;
		}
		else {
			 return (File) null;
		}
		
	}
    
    @FXML
    protected void onRepLecBtn(){
    	repLec = chooseRepLec();
    	rep_lec_btn.setText(repLec.toString());
    	sel.setFile(repLec);
    	maj();
    }
    @FXML
    protected void onExportBtn(){
    	export = chooseExport();

    	String [] command_m = new String [] {settings.getMencoder().toString(),
    			                           "-nosound",
    			                           "-ovc",
    			                           "copy",
    			                           "-o",
    			                           "/tmp/out.mov",
    			                           //"-really-quiet",
    			                           String.format("mf://@%s", settings.getListe().toString()),
    			                           "-mf",
    			                           "fps=25:type=jpg"};
    	String [] command_f = new String [] {
    			                           settings.getFfmpeg().toString(),
    			                           "-i",
    			                           "/tmp/out.mov",
    			                           "-vcodec",
    			                           "prores",
    			                           "-an",
    			                           export.toString().toString()};   
    	
    	System.out.println(Arrays.asList(command_m).stream().collect(Collectors.joining(" ")));
    	

    	BufferedReader is;
		
		new Thread(new Runnable(){
		
			@Override
			public void run() {
				Process p;
				try {
					p = new ProcessBuilder(command_m).start();
					p.getOutputStream().close();

					AfficheurFlux fluxSortie = new AfficheurFlux(p.getInputStream(), pgrs_bar, (int)slider.getMax());
		            //AfficheurFlux fluxErreur = new AfficheurFlux(p.getErrorStream(), pgrs_bar, slider.getMax());
		            
		            
		            new Thread(fluxSortie).start();
		            //new Thread(fluxErreur).start();
		            
		            p.waitFor();
		            
		            p.getInputStream().close();
		            p.getErrorStream().close();
		            
		            System.out.println(Arrays.asList(command_f).stream().collect(Collectors.joining(" ")));
		            
		            new Thread(new Runnable(){
		    			
		    			@Override
		    			public void run() {
		    				Process p;
		    				try {
		    					p = new ProcessBuilder(command_f).start();
		    					p.getOutputStream().close();

		    					//AfficheurFlux fluxSortie = new AfficheurFlux(p.getInputStream(), pgrs_bar_2, slider.getMax());
		    		            AfficheurFlux fluxErreur = new AfficheurFlux(p.getErrorStream(), pgrs_bar_2, (int)slider.getMax());
		    		            
		    		            
		    		            //new Thread(fluxSortie).start();
		    		            new Thread(fluxErreur).start();
		    		            
		    		            p.waitFor();
		    		            
		    		            p.getInputStream().close();
		    		            p.getErrorStream().close();
		    					
		    				} catch (IOException | InterruptedException e) {
		    					// TODO Bloc catch généré automatiquement
		    					e.printStackTrace();
		    				}
		    				
		    			}
		    		}).start();
					
				} catch (IOException | InterruptedException e) {
					// TODO Bloc catch généré automatiquement
					e.printStackTrace();
				}
				
			}
		}).start();

    }
    
    @FXML
    protected void onSettingsBtn(){
    	

 	
    	Stage stageSettins = new Stage();
    	TitledPane tp = new TitledPane();
    	Scene scene = new Scene((Parent) JfxUtils.loadFxml("uiSettings.fxml"), 500, 400);
    	stageSettins.setScene(scene);
    	stageSettins.setAlwaysOnTop(true);
    	stageSettins.show();
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		home =  System.getProperty("user.home");
		settings_file = new File(home, "ts_select.conf");
		
		if ( ! settings_file.exists()){
			onSettingsBtn();
		}else{
			FileReader fr = null;
			try {
				fr = new FileReader(settings_file);
			} catch (FileNotFoundException e1) {
				// TODO Bloc catch généré automatiquement
				e1.printStackTrace();
			}
	    	BufferedReader br = new BufferedReader(fr);
	    	
	    	String s = null;
			try {
				s = br.readLine();
			} catch (IOException e1) {
				// TODO Bloc catch généré automatiquement
				e1.printStackTrace();
			}
	    	while(s != null){
	    		String key = s.split(":")[0];
	    		String value = s.split(":")[1];
	    		switch (key){
	    		
	    		case "ffmpeg" : settings.setFfmpeg(new File(value));
	    		                break;
	    		case "mencoder" : settings.setMencoder(new File(value));
	    		                  break;
	    		case "liste" : settings.setListe(new File(value));
	    		
	    		}
	    		try {
					s = br.readLine();
				} catch (IOException e) {
					
					e.printStackTrace();
					break;
				}
	    	}
	    	try {
				fr.close();
			} catch (IOException e) {
				// TODO Bloc catch généré automatiquement
				e.printStackTrace();
			}
		}

		
		plages.addAll(new String [] {"selectionner une plage",
                                     "1 image",
                                     "2 images",
                                     "5 images",
                                     "10 images",
                                     "15 images",
                                     "20 images",
                                     "30 images"});
		plage.setItems(plages);
		plage.getSelectionModel().select(0);
		plage.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	                public void changed(ObservableValue ov,
	                    Number value, Number new_value) {
	                	if (new_value.intValue()!= 0){
	                	    sel.setPlage(Integer.parseInt(plages.get((int) new_value).split(" ")[0]));
	                	    maj();
	                	}
	            }
		});
		
		decalages.addAll(new String [] {"selectionner un decalage",
                "1 minute",
                "5 minutes",
                "10 minutes",
                "15 minutes",
                "20 minutes",
                "25 minutes",
                "30 minutes",
                "35 minutes",
                "45 minutes",
                "60 minutes"
                });
       decalage.setItems(decalages);
       decalage.getSelectionModel().select(0);
       decalage.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	                public void changed(ObservableValue ov,
	                    Number value, Number new_value) {
	                	if (new_value.intValue()!= 0){
	                	    sel.setDecalage(Integer.parseInt(decalages.get((int) new_value).split(" ")[0]));
	                	    maj();
	                	}
	            }
		});
       
       debuts.addAll(new String [] {"selectionner un debut",
               "5h00",
               "6h00",
               "7h00",
               "8h00",
               "9h00",
               "10h00",
               "11h00",
               "12h00",
               "13h00",
               "14h00",
               "15h00",
               "16h00",
               "17h00",
               "18h00",
               "19h00",
               "20h00",
               "21h00"               
               });
      debut.setItems(debuts);
      debut.getSelectionModel().select(0);
      debut.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	                public void changed(ObservableValue ov,
	                    Number value, Number new_value) {
	                	if (new_value.intValue()!= 0){
	                	    sel.setDebut(Integer.parseInt(debuts.get(new_value.intValue()).split("h")[0]));
	                	    maj();
	                	}
	            }
		});
              
       StringBinding sb = new StringBinding() {
    	   {
              super.bind(slider.valueProperty());
    	   }
	
		@Override
		protected String computeValue() {
			
			if (aff.size() > 0){
			
				num.set((int) slider.valueProperty().get());
				
				String tmp = aff.get(num.get()).toString();
				
				nom.set(num.get() + "    " + tmp);
				
				tmpImage = new Image("file://" + tmp);
				
				sbi.set(tmpImage);
			}
			return nom.get();
		}
	};
   
   label.textProperty().unbind();

   label.textProperty().bind(sb);

   view.imageProperty().bind(sbi);

	}
	
	protected void maj(){
		
    	
    	aff = SelectUtil.select(sel, settings.getListe() ); 
    	
    	if (aff != null && aff.size() > 0){
    		slider.setMax(aff.size() -1);
    	}
    	slider.setMin(0);
    	slider.isSnapToTicks();
		
	}

	public static Settings getSettings() {
		return settings;
	}

	public static void setSettings(Settings settings) {
		TSUIController.settings = settings;
	}
	
	
	

}
