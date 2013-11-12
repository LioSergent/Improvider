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
import android.widget.RelativeLayout.LayoutParams;

// COUCOU PIGNOUF

public class Improvider extends Activity {

	public Button boutonJouer; // Bouton jouer
	public Button boutonCommencer; // Bouton Infos
	public Button boutonCredits; // Bouton Cr�dits

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_improvider);

		boutonJouer = (Button) findViewById(R.id.bouton_partie_rapide);
		boutonJouer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// On passe � l'activit� ChoixAccompagnement
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
				// On passe � l'activit� ImproviderInfos
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

				// On passe � l'activit� ImproviderCr�dits
				Intent explicit = new Intent();
				explicit.setClassName("com.improvider",
						"com.improvider.ImproviderCredits");
				startActivity(explicit);

			}
		});
		
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
					
			       float diagonalInch=(float) Math.sqrt(dpHeight*dpHeight+dpWidth*dpWidth)/160;
			  
					
			        if (diagonalInch<4) {
			        
			        	
			        	
			        	boutonCommencer.setTextSize(26);
			        	boutonJouer.setTextSize(26);
			        	boutonCredits.setTextSize(26);
			      
			        	
			        }
			        
			        }

		}
	
	
}
