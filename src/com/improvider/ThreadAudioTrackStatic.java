package com.improvider;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class ThreadAudioTrackStatic extends Thread {

	public byte[] abData;
	public final int bufferSize = 172000;
	public boolean isStop = false;
	public AudioTrack at = null;
	public int taille;
	public final int header=44;
  
	public ThreadAudioTrackStatic(byte[] donnees) {
		abData = donnees;
		taille = abData.length;
	}

	
public void pause() {
	at.stop();	
}

	public void run() {

		

		AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, taille, AudioTrack.MODE_STATIC);

		if (at != null) {
		at.write(abData, header, abData.length - header);	
		at.play(); 
					

Log.d("TCAudio", "ici");
			at.stop();
			at.release();
		} else {
			Log.d("TCAudio", "audio track is not initialised ");
		}
	}

	public void stopSansWhile() {
		this.at.stop();
	}

}