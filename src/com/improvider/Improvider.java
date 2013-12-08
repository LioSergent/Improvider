package com.improvider;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Activité initiale, lançée lors du démarrage de l'app. Contient donc le menu
 * principal
 * 
 * @author Lionel
 * 
 */

public class Improvider extends Activity {

	public Button boutonJouer; // Bouton jouer
	public Button boutonCommencer; // Bouton Infos
	public Button boutonCredits; // Bouton Crï¿½dits
    public Toast toast;
    public Scale scaleTest;
    public Session sessionTest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_improvider);
		final Context context = getApplicationContext();
		double[] intervallesPentaMineure={1.5,1,1,1.5,1};
		scaleTest= new Scale("LLLLLLLLOL", com.improvider.NameNote.DO, intervallesPentaMineure);
		int[] sepa={250,600};
		Scale[] tabScale={scaleTest};
		sessionTest=new Session("LOLBIS", R.raw.barbluesaminor, tabScale,sepa);
		boutonJouer = (Button) findViewById(R.id.bouton_partie_rapide);
		boutonJouer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d("scaleTestName", sessionTest.getNom());
				toast = Toast.makeText(context,sessionTest.getNom() , Toast.LENGTH_LONG);
				toast.show();
				// On passe ï¿½ l'activitï¿½ ChoixAccompagnement
				Intent explicit = new Intent();
				explicit.setClassName("com.improvider",
						"com.improvider.ChoixAccompagnement");
				startActivity(explicit);

			}
		});

		boutonCommencer = (Button) findViewById(R.id.bouton_commencer);
		boutonCommencer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// On passe ï¿½ l'activitï¿½ ImproviderInfos
				Intent explicit = new Intent();
				explicit.setClassName("com.improvider",
						"com.improvider.Commencer");
				startActivity(explicit);

			}
		});

		boutonCredits = (Button) findViewById(R.id.boutonCredits);
		boutonCredits.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// On passe ï¿½ l'activitï¿½ ImproviderCrï¿½dits
				Intent explicit = new Intent();
				explicit.setClassName("com.improvider",
						"com.improvider.ImproviderCredits");
				startActivity(explicit);

			}
		});

		// Redimensionnement du texte pour les appareil Normal-petits
		reSizeNormal();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.improvider, menu);
		return true;
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

				boutonCommencer.setTextSize(26);
				boutonJouer.setTextSize(26);
				boutonCredits.setTextSize(26);

			}

		}

	}

}
