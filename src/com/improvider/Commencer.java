package com.improvider;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Commencer extends Activity {
	Button boutonMontrer;
	Button boutonSavoir;
	Button boutonRetour;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commencer);
		boutonMontrer = (Button) findViewById(R.id.bouton_montre_moi);

		boutonMontrer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// On passe � l'activit� ChoixAccompagnement
				Intent explicit = new Intent();
				explicit.setClassName("com.improvider",
						"com.improvider.Tutoriel");
				startActivity(explicit);

			}
		});

		boutonSavoir = (Button) findViewById(R.id.bouton_savoir_plus);

		boutonSavoir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// On passe � l'activit� ChoixAccompagnement
				Intent explicit = new Intent();
				explicit.setClassName("com.improvider",
						"com.improvider.SavoirPlus");
				startActivity(explicit);

			}
		});

		boutonRetour = (Button) findViewById(R.id.bouton_commencer_retour);

		boutonRetour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
				// On passe � l'activit� ChoixAccompagnement
				Intent explicit = new Intent();
				explicit.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				explicit.setClassName("com.improvider",
						"com.improvider.Improvider");
				startActivity(explicit);

			}
		});
		reSizeNormal();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.commencer, menu);
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
			
	       float diagonalInch=(float) Math.sqrt(dpHeight*dpHeight+dpWidth*dpWidth)/160;
	    Log.d("Diagon", String.valueOf(diagonalInch));
			
	        if (diagonalInch<4) {
	        
	        	
	        	
	        	boutonMontrer.setTextSize(17);
	        	boutonSavoir.setTextSize(17);
	        	boutonRetour.setTextSize(17);
	      TextView texte=(TextView) findViewById(R.id.text_commencer);
	        	texte.setTextSize(21);
	        }
	        
	        }

}
	
}
