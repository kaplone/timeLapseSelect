package timeSelect;

import java.io.File;

public class Settings {
	
	private File ffmpeg;
	private File mencoder;
	private File liste;
	
	
	
	public Settings(File ffmpeg, File mencoder, File liste) {
		super();
		this.ffmpeg = ffmpeg;
		this.mencoder = mencoder;
		this.liste = liste;
	}
	
	public Settings() {
		// TODO Stub du constructeur généré automatiquement
	}

	public File getFfmpeg() {
		return ffmpeg;
	}
	public void setFfmpeg(File ffmpeg) {
		this.ffmpeg = ffmpeg;
	}
	public File getMencoder() {
		return mencoder;
	}
	public void setMencoder(File mencoder) {
		this.mencoder = mencoder;
	}
	public File getListe() {
		return liste;
	}
	public void setListe(File liste) {
		this.liste = liste;
	}

}
