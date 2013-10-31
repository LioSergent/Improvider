package com.improvider;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class ThreadAudioTrack extends Thread {
	// Nom du fichier wav à lire, stocké dans le dossier assets de l'application
	public String adresse;
	// AssetManager: sert à ouvrir le fichier wav
	public AssetManager assetManager;
	// Le stream de lecture
	InputStream in = null;
	// La line de son
	public AudioTrack at;
	// Taille du tableau donné à l'AudioTrack
	private int bufferSize;
	// Variable d'arrêt
	public boolean isPaused = false;
	// La taille de l'en-tête de la plupart des fichier wav
	public final int header = 44;

	// Un constructeur, l'AssetManager vient du contexte, donc de l'activité.

	public ThreadAudioTrack(String path, AssetManager manager) {
		adresse = path;
		assetManager = manager;
		in = null;
		// La taille minimale du buffer(que le prendra comme buffer) nous est
		// donné par une méthode d'AudioTrack
		bufferSize = android.media.AudioTrack.getMinBufferSize(44100,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);

		// Sert à l'afficher
		String MinBuffSize = String.valueOf(bufferSize);
		Log.d("TCAudio", MinBuffSize);

		// Création de l'AudioTrack
		at = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, bufferSize,
				AudioTrack.MODE_STREAM);
		at.setStereoVolume((float)1,(float) 1);
	}

	// Getters et Setters (pour arrêter, changer le volume... mais j'arrive pas encore à les faire marcher)

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setisStopTrue() {
		isPaused = true;
	}

	public void setisStopFalse() {
		isPaused = false;
	}

	public void setVolume(float v) {
		at.setStereoVolume(v, v);
	}

	// La méthode Run

	public void run() {
		// Le tableau dans lequel sont rangées les données audio.
		byte[] abData = null;
		InputStream in = null;

		// Un big Try/Catch pour pas trop se faire chier.

		//AssetManager asset = getAssets();
		//InputStream in=asset.open(adresse);
		//long fileSize = ad.getLength();
		//in.read(abData, 0, 0);
		
		
		try {
			// Ouverture du fichier, ad ne sert pas finalement.
			in = assetManager.open(adresse);
			AssetFileDescriptor ad = assetManager.openFd(adresse);

			// Des données qui me servent plus ou moins (c ici ne sert pas)
			long fileSize = ad.getLength();
			Log.d("TCAudio", String.valueOf(fileSize));
			int c = in.available();
			Log.d("TCAudio", String.valueOf(c));

			// Création du tableau et de deux variables de comptage.
			abData = new byte[bufferSize];
			int bytesWritten = 0;
			int bytesRead = 0;

			// On dit à l'AudioTrack de jouer, ne me demandez pas trop
			// pourquoi...
			at.play();

			// On lit dans le vide l'en-tête.
			in.read(abData, 0, header);

			// Boucle de lecture
			int length;
			while (!isPaused && (length=in.read(abData))!=-1) {

	//		while (!isPaused && bytesWritten < fileSize - header) {

				bytesRead = in.read(abData, 0, abData.length);
				bytesWritten += bytesRead;
				// C'est cette ligne de code qui envoie la data dans la carte son.
				at.write(abData, 0, abData.length);

				Log.d("TCAudio", String.valueOf(bytesWritten));
			}

			// On ferme
			at.stop();
			at.release();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
	
	
	/* IMPORTANT: Pour l'utiliser à partir d'une activité il faut écrire un truc du type:
	 * 
	                AssetManager asset = getAssets();
	                String adresse="DoPiano.wav";
	  
					PlayThread sonBouton1 = new PlayThread(adresse, asset);
					sonBouton1.start();
	 * 
	 * 
	 */
