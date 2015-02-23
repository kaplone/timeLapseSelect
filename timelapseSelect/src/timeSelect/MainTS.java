package timeSelect;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainTS extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene((Parent) JfxUtils.loadFxml("ui.fxml"), 1520, 900);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
		// selection :
		// mencoder mf://@liste_select.txt -mf w=1000:h=750:fps=25:type=jpg -ovc copy -oac copy -o sortie9.mov
		//
		// complet pour client :
		// mencoder mf://../soletanche/*.jpg -mf fps=25:type=jpg -ovc x264 subq=5:partitions=all:8x8dct:frameref=2:bframes=3:b_pyramid=normal:weight_b -o ../soletanche.mp4
	}
}
