package com.improvider;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

/*
 * Classe qui gère la lecture des fichiers média. Les boutons sont définis dans le XML. On les récupère ici pour gérer les actions.
 */
public class Musique extends Activity implements OnClickListener {

	MediaPlayer player;
	boolean enCoursLecture = false; // Pour savoir si l'on est en train de jouer
									// un morceau
	Button boutonPlay;
	Context context; // Permettra de récupérer les informations concernant
						// l'instance d'Improvider
	View view;
	TextView Auteur;
	TextView tempsMax;
	int volumePlayer;

	/*
	 * Constructeur appelé depuis Improvider.java
	 */

	public Musique(View v, Context c) {
		context = c;
		view = v;
		boutonPlay = (Button) v.findViewById(R.id.boutonPlay);
		boutonPlay.setBackgroundResource(R.drawable.play);
		Auteur = (TextView) v.findViewById(R.id.auteur);
		this.setAuteur("...");

		// Association du bouton Play à la lecture / pause du morceau
		boutonPlay.setOnClickListener(this);

	}

	public void onClick(View v) {
		if (enCoursLecture) {
			player.pause();
			enCoursLecture = false;
			boutonPlay.setBackgroundResource(R.drawable.play);

		} else if (!enCoursLecture) {
			player.start();
			enCoursLecture = true;
			boutonPlay.setBackgroundResource(R.drawable.pause);
		}

	}

	public void mettreEnPause() {
		if (enCoursLecture) {
			player.pause();
			boutonPlay.setBackgroundResource(R.drawable.play);
			enCoursLecture = false;
		}
	}

	public void couperMusique() {
		player.stop();
	}

	public int getPosition() {
		return player.getCurrentPosition();
	}

	public void setPosition(int i) {
		player.seekTo(i);
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

}
