package com.improvider;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Classe qui gère la lecture des fichiers média. Les boutons sont définis dans
 * le XML. On les récupère ici pour gérer les actions.
 */
public class Musique extends Activity  {

	MediaPlayer player;
	boolean enCoursLecture = false; // Pour savoir si l'on est en train de jouer
									// un morceau

	
	Context context; // Permettra de rÃ©cupÃ©rer les informations concernant
						// l'instance du main
	View view;
	TextView Auteur;
	TextView tempsMax;
	int volumePlayer;

	/*
	 * Constructeur appelÃ© depuis Main.java
	 */

	public Musique(View v, Context c) {
		context = c;
		view = v;
	
	
	}

	

	
	public void couperMusique() {
		player.stop();
	}

	public int getPosition() {
		return player.getCurrentPosition();
	}

	public int getDuration() {
		return player.getDuration();
	}

	public void setPlayer(int i) {
		player = MediaPlayer.create(context, i);
	}

	public void setVolume(int i) {
		player.setVolume(((float) i) / 100, ((float) i) / 100);

	}

	public void setAuteur(String i) {
		Auteur.setText(i);
	}

	public void setPosition(int i) {
		player.seekTo(i);
	}
   public void play() {
	   player.start();
	   enCoursLecture = true;
	
   }
   
   public void pause() {
	   player.pause();
	   enCoursLecture=false;
	  
   }
}
