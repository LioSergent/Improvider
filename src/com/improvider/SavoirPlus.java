package com.improvider;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Activité pour obtenir une explication "d'où vient l'app", que l'on peut
 * obtenir depuis l'activité Commencer. Renvoie vers tuto ou Commencer.
 * 
 * @author Lionel
 * 
 */
public class SavoirPlus extends Activity {

	Button boutonMontrer;
	Button boutonRetour;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_savoir_plus);

		boutonMontrer = (Button) findViewById(R.id.bouton_montre_moi_bis);

		boutonMontrer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// On passe ï¿½ l'activitï¿½ ChoixAccompagnement
				Intent explicit = new Intent();
				explicit.setClassName("com.improvider",
						"com.improvider.Tutoriel");
				startActivity(explicit);

			}
		});

		boutonRetour = (Button) findViewById(R.id.bouton_retour_savoir);

		boutonRetour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
				// On passe ï¿½ l'activitï¿½ ChoixAccompagnement
				Intent explicit = new Intent();
				explicit.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				explicit.setClassName("com.improvider",
						"com.improvider.Commencer");
				startActivity(explicit);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.savoir_plus, menu);
		return true;
	}

	 public void onStart() {
		    super.onStart();
		
		    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
		  }
	 public void onStop() {
			super.onStop();
			EasyTracker.getInstance(this).activityStop(this);  // Add this method.
		}
	
}
