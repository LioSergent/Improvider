package com.improvider;

import com.google.analytics.tracking.android.EasyTracker;
import com.improvider.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Activité d'affichage des crédits
 * 
 * @author Lionel
 * 
 */
public class ImproviderCredits extends Activity {
	Button boutonCreditsRetour;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_improvider_credits);

		Button boutonCreditsRetour = (Button) findViewById(R.id.bouton_credits_retour);
		boutonCreditsRetour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.improvider_credits, menu);
		return true;
	}

	
	 public void onStart() {
		    super.onStart();
		    if (BuildMode.PROD) {
		    EasyTracker.getInstance(this).activityStart(this); 
		    }// Add this method.
		  }
	 public void onStop() {
			super.onStop();
			if (BuildMode.PROD) {
			EasyTracker.getInstance(this).activityStop(this); 
			}// Add this method.
		}
}
