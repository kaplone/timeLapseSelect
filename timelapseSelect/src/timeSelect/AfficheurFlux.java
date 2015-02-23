package timeSelect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

class AfficheurFlux implements Runnable {

    private final InputStream inputStream;
    private final ProgressBar pgb;
    private int max;
    int i = 0;
    private Task task ;

    AfficheurFlux(InputStream inputStream,  ProgressBar pgb, int max) {
        this.inputStream = inputStream;
        this.pgb = pgb;
        this.max  = max;

    }

    private BufferedReader getBufferedReader(InputStream is) {
        return new BufferedReader(new InputStreamReader(is));
    }

    @Override
    public void run() {
    	
//    	this.task = new Task<Void>() {
//    	    @Override public Void call() {
//    	        updateProgress(i, max);
//    	        
//    	        return null;
//    	    }
//    	};
        
//        pgb.progressProperty().bind(this.task.progressProperty());
    	
//    	new Thread(this.task).start();
    	
        BufferedReader br = getBufferedReader(inputStream);
        String ligne = "";
    	
        try {
            while ((ligne = br.readLine()) != null) {
            	if (ligne.startsWith("Pos:")){
            		// Pos:   5.6s    140f (100%)  0.00fps Trem:   0min  39mb  A-V:0.000 [59062:0]
            		System.out.println(ligne.split("f")[0].split("s")[2].trim());
            		i = Integer.parseInt(ligne.split("f")[0].split("s")[2].trim());
            		Platform.runLater(() -> pgb.setProgress((double)(i)/max));
            	}
            	else if (ligne.startsWith("frame=")){
            		// frame=  140 fps= 38 q=0.0 Lsize=   42337kB time=00:00:05.60 bitrate=61933.3kbits/s
            		System.out.println(ligne.split("fps")[0].split("=")[1].trim());
            		i = Integer.parseInt(ligne.split("fps")[0].split("=")[1].trim());
            		Platform.runLater(() -> pgb.setProgress((double)(i)/max));
            	}
            	
            	//System.out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
