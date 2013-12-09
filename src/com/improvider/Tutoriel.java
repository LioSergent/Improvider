package com.improvider;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Classe manipulant tout le tutoriel (mais après l'activité Commencer). Elle
 * sert à switcher les différentes "diapos".
 * 
 * @author Lionel
 * 
 */
public class Tutoriel extends Activity {

	TextView explication;
	ImageView image;
	Button boutonPrecedent;
	Button boutonSuivant;
	int widthScreen;
	int heightScreen;
	int imageWidth;
	int imageHeight;
	double proportion = 0.6;
	double proportionNexus4 = 0.52;
	Toast toast;
	int state = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutoriel);
		final Context context = getApplicationContext();
		explication = (TextView) findViewById(R.id.text_tutoriel);

		// Gestion des évènements des boutons du tutoriel

		boutonPrecedent = (Button) findViewById(R.id.bouton_tutoriel_precedent);
		boutonPrecedent.setText(R.string.retour);
		boutonPrecedent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (state == 0) {

					finish();
					Intent explicit = new Intent();
					explicit.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					explicit.setClassName("com.improvider",
							"com.improvider.Commencer");
					startActivity(explicit);
				}

				else {
					diapoPrecedente(state);
				}

			}
		});

		boutonSuivant = (Button) findViewById(R.id.bouton_tutoriel_suivant);
		boutonSuivant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (state == 3) {
					toast = Toast.makeText(context, R.string.chargement,
							Toast.LENGTH_LONG);
					toast.show();

					Handler lHandler = new Handler();

					lHandler.postDelayed(new Runnable() {
						public void run() {
							Intent explicit = new Intent();
							explicit.setClassName("com.improvider",
									"com.improvider.Main");
							explicit.putExtra("Adresse",
									R.raw.bluessoulguitarbackingtrackineminor);
							// On rajoute le code de la session
							
							explicit.putExtra("numeroSession", 4);
					
							startActivity(explicit);

						}

					}, 30);

				}

				else {
					diapoSuivante(state);
				}
			}
		});

		// On s'occupe de layouter un peu plus précisemment certains éléments
		// En fonction de la taille de la diagonale

		// Calcul de la diagonale
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		widthScreen = metrics.widthPixels;
		heightScreen = metrics.heightPixels;
		float density = getResources().getDisplayMetrics().density;

		float dpHeight = heightScreen / density;
		float dpWidth = widthScreen / density;

		float diagonalInch = (float) Math.sqrt(dpHeight * dpHeight + dpWidth
				* dpWidth) / 160;

		// Redimensionnement de l'image
		proportion = 0.48 + diagonalInch * 0.012;

		imageWidth = (int) (widthScreen * proportion);
		imageHeight = (int) (imageWidth * proportionNexus4);

		image = (ImageView) findViewById(R.id.image_tutoriel);
		image.setMaxHeight(imageHeight);
		image.setMaxWidth(imageWidth);

		// Redimensionnement du texte
		reSizeTexte(diagonalInch);
	}

	// Méthodes de cycle de vie
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tutoriel, menu);
		return true;
	}

	public void onDestroy() {
		super.onDestroy();
		if (toast != null) {

			toast.cancel();
		}
	}

	public void onStop() {
		super.onStop();
		if (toast != null) {

			toast.cancel();
		}
	}

	public void onPause() {
		super.onPause();
		if (toast != null) {

			toast.cancel();
		}

	}

	public void onBackPressed() {

		if (state > 0) {
			this.diapoPrecedente(state);

		} else {
			finish();
		}

	}

	// Méthodes de navigation entre les diapos
	private void premiereDiapo() {

		boutonPrecedent.setText(R.string.retour);
		explication.setText(R.string.diapotuto1);
		image.setImageResource(R.drawable.diapotutomorceau);
	}

	private void deuxiemeDiapo() {
		boutonPrecedent.setText(R.string.precedent);
		explication.setText(R.string.diapotuto2);
		image.setImageResource(R.drawable.diapotutopiano1);

	}

	private void troisiemeDiapo() {

		explication.setText(R.string.diapotuto3);
		image.setImageResource(R.drawable.diapotutopiano2);
		boutonSuivant.setText(R.string.suivant);
	}

	private void quatriemeDiapo() {

		boutonSuivant.setText(R.string.letgo);
		explication.setText(R.string.diapotuto4);
		image.setImageResource(R.drawable.diapotutoreglages);
	}

	private void setState(int i) {
		this.state = i;
	}

	private boolean diapoPrecedente(int i) {

		if (i == 1) {
			premiereDiapo();
			setState(0);
			return true;
		}

		if (i == 2) {
			deuxiemeDiapo();
			setState(1);
			return true;
		}

		if (i == 3) {
			troisiemeDiapo();
			setState(2);
			return true;
		}
		return false;
	}

	private boolean diapoSuivante(int i) {

		if (i == 0) {
			deuxiemeDiapo();
			setState(1);
			return true;
		}

		if (i == 1) {
			troisiemeDiapo();
			setState(2);
			return true;
		}

		if (i == 2) {
			quatriemeDiapo();
			setState(3);
			return true;
		}

		return false;
	}

	/**
	 * Méthode de redimensionnement
	 * 
	 * @param diagonalInch
	 */
	private void reSizeTexte(float diagonalInch) {

		if (diagonalInch < 4) {

			Button texte = (Button) findViewById(R.id.text_tutoriel);
			texte.setTextSize(17);
		}

	}
}
