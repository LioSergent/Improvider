package com.improvider;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Tutoriel extends Activity {

	TextView explication;
	ImageView image;
	Button boutonPrecedent;
	Button boutonSuivant;
	int screenWidth;
	int screenHeight;
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

		if (android.os.Build.VERSION.SDK_INT >= 13) {
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			screenWidth = size.x;
			screenHeight = size.y;

		} else {
			Display display = getWindowManager().getDefaultDisplay();
			screenWidth = display.getWidth();
			screenHeight = display.getHeight();

			// the constructor which you are calling
		}

		// Petit r√©glage selon la taille de l'√©cran. C'est sale, mais je
		// voulais le faire t√¥t juste pour le tuto.

		if (screenHeight < 500) {
			proportion = 0.37;
		}

		if (screenHeight < 700) {
			proportion = 0.45;
		}

		if (screenHeight > 1000) {
			proportion = 0.53;
		}

		if (screenHeight > 1200) {
			proportion = 0.58;
		}

		if (screenHeight > 1400) {
			proportion = 0.64;
		}

		// En fonction de la taille rÈelle de l'Ècran.

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int widthScreen = metrics.widthPixels;
		int heightScreen = metrics.heightPixels;
		float density = getResources().getDisplayMetrics().density;

		float dpHeight = heightScreen / density;
		float dpWidth = widthScreen / density;

		float diagonalInch = (float) Math.sqrt(dpHeight * dpHeight + dpWidth
				* dpWidth) / 160;

		proportion = 0.48 + diagonalInch * 0.012;

		imageWidth = (int) (screenWidth * proportion);
		imageHeight = (int) (imageWidth * proportionNexus4);

		explication = (TextView) findViewById(R.id.text_tutoriel);
		image = (ImageView) findViewById(R.id.image_tutoriel);

		image.setMaxHeight(imageHeight);
		image.setMaxWidth(imageWidth);

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
					toast = Toast.makeText(context, R.string.chargement, 20000);
					toast.show();

					Handler lHandler = new Handler();

					lHandler.postDelayed(new Runnable() {
						public void run() {
							Intent explicit = new Intent();
							explicit.setClassName("com.improvider",
									"com.improvider.Main");
							explicit.putExtra("Adresse",
									R.raw.bluessoulguitarbackingtrackineminor);
							boolean[] value = { false, true, true, false, true,
									true, true, false, false, false, false,
									false, false, false };
							explicit.putExtra("Gamme", value);
							explicit.putExtra("name", "Blues Soul Em");
							explicit.putExtra("Tonique", 2);
							startActivity(explicit);

						}

					}, 30);

				}

				else {
					diapoSuivante(state);
				}
			}
		});

		reSizeNormal();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tutoriel, menu);
		return true;
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

	private void reSizeNormal() {

		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
			DisplayMetrics metrics = getResources().getDisplayMetrics();
			int widthScreen = metrics.widthPixels;
			int heightScreen = metrics.heightPixels;
			float density = getResources().getDisplayMetrics().density;

			float dpHeight = heightScreen / density;
			float dpWidth = widthScreen / density;

			float diagonalInch = (float) Math.sqrt(dpHeight * dpHeight
					+ dpWidth * dpWidth) / 160;

			if (diagonalInch < 4) {

				Button texte = (Button) findViewById(R.id.text_tutoriel);
				texte.setTextSize(17);
			}

		}

	}
}
