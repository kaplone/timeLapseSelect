package timeSelect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UISettingsController  implements Initializable {
	
	@FXML
	private Button ffmpeg_btn;
	@FXML
	private TextField ffmpeg_txt;
	@FXML
	private Button mencoder_btn;
	@FXML
	private TextField mencoder_txt;
	@FXML
	private Button liste_btn;
	@FXML
	private TextField liste_txt;
	
	private FileWriter fw;
	
	@FXML
	protected void onFfmpegBtn(){
		settings.setFfmpeg(chooseFfmpeg());
		ffmpeg_txt.setText(settings.getFfmpeg().toString());
		ecrire();
	}
	@FXML
	protected void onMencoderBtn(){
		settings.setMencoder(chooseMencoder());
		mencoder_txt.setText(settings.getMencoder().toString());
		ecrire();
	}
	@FXML
	protected void onListeBtn(){
		settings.setListe(chooseListe());
		liste_txt.setText(settings.getListe().toString());
		ecrire();
	}
	
	protected void ecrire(){
		try {
			fw = new FileWriter(settings_file);
			fw.write(String.format("ffmpeg:%s\nmencoder:%s\nliste:%s", settings.getFfmpeg().toString(), settings.getMencoder().toString(), settings.getListe().toString()));
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		
	}
	
	private File settings_file ;
	private Settings settings;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		String home =  System.getProperty("user.home");
		settings_file = new File(home, "ts_select.conf");
		
		settings = TSUIController.getSettings();
		
		if (settings_file.exists()){
			
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
	    		                ffmpeg_txt.setText(value);
	    		                break;
	    		case "mencoder" : settings.setMencoder(new File(value));
	    		                  mencoder_txt.setText(value);
	    		                  break;
	    		case "liste" : settings.setListe(new File(value));
	    		               liste_txt.setText(value);
	    		
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
        try {
			fw = new FileWriter(settings_file);
		} catch (IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		
		
	}

	
    protected File chooseFfmpeg(){
		
		Stage newStage = new Stage();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("rechercher ffmpeg");
		fileChooser.getExtensionFilters().addAll(
		         new FileChooser.ExtensionFilter("ffmpeg", "ffmpeg"));
		File selectedFile = fileChooser.showOpenDialog(newStage);
		if (selectedFile != null) {
			 return selectedFile;
		}
		else {
			 return (File) null;
		}
		
	}
    
    protected File chooseMencoder(){
		
		Stage newStage = new Stage();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("rechercher mencoder");
		fileChooser.getExtensionFilters().addAll(
		         new FileChooser.ExtensionFilter("mencoder", "mencoder"));
		File selectedFile = fileChooser.showOpenDialog(newStage);
		if (selectedFile != null) {
			 return selectedFile;
		}
		else {
			 return (File) null;
		}
		
	}
    protected File chooseListe(){
		
		Stage newStage = new Stage();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("rechercher liste");
		fileChooser.getExtensionFilters().addAll(
		         new FileChooser.ExtensionFilter("listes", ".txt"));
		File selectedFile = fileChooser.showSaveDialog(newStage);
		if (selectedFile != null) {
			 return selectedFile;
		}
		else {
			 return (File) null;
		}
		
	}

}
