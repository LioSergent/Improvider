package com.improvider;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.improvider.R;

/**
 * Activité servant à demander à l'utilisateur son choix quand à
 * l'accompagnement. Classique.
 * 
 * @author Lionel
 * 
 */

public class ChoixAccompagnement extends Activity {
	// Les 5 boutons correspondants aux morceaux
	private Button boutonBarBlues;
	private Button boutonBluesSoul;
	private Button boutonHipHop;
	private Button boutonAcoustic;
	private Button boutonHardRock;
	private Button boutonChoixRetour;
	/**
	 * Le toast sert à afficher "Chargement"
	 * 
	 */
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_accompagnement);
		final Context context = getApplicationContext();

		
		boutonBarBlues = (Button) findViewById(R.id.boutonBarBlues);
		boutonBarBlues.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				toast = Toast.makeText(context, R.string.chargement,
						Toast.LENGTH_LONG);
				toast.show();
				// on crï¿½e un intent pour passer ï¿½ l'activitï¿½ Improvider
				// Le handler sert a retarder le lancement, sinon le toast a pas
				// le temps de show
				if (BuildMode.DEBUG) {
				sendToTrackerSession("Bar Blues");
				}
				
				Handler lHandler = new Handler();

				lHandler.postDelayed(new Runnable() {
					public void run() {
						Intent explicit = new Intent();
						// On spï¿½cifie la prochaine activitï¿½
						explicit.setClassName("com.improvider",
								"com.improvider.Main");
						// On rajoute le code de la session

						explicit.putExtra("numeroSession", 1);

						startActivity(explicit);

					}

				}, 30);

			}
		});
		boutonBluesSoul = (Button) findViewById(R.id.boutonBluesSoul);
		boutonBluesSoul.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toast = Toast.makeText(context, R.string.chargement,
						Toast.LENGTH_LONG);
				toast.show();
				if (BuildMode.DEBUG) {
				sendToTrackerSession("Blues Soul");
				}
				Handler lHandler = new Handler();

				lHandler.postDelayed(new Runnable() {
					public void run() {
						Intent explicit = new Intent();
						explicit.setClassName("com.improvider",
								"com.improvider.Main");
						// On rajoute le code de la session

						explicit.putExtra("numeroSession", 2);

						startActivity(explicit);

					}

				}, 30);

			}
		});

		boutonHipHop = (Button) findViewById(R.id.boutonHipHop);
		boutonHipHop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toast = Toast.makeText(context, R.string.chargement,
						Toast.LENGTH_LONG);
				toast.show();
				if (BuildMode.DEBUG) {
				sendToTrackerSession("Hip Hop");
				}
				Handler lHandler = new Handler();

				lHandler.postDelayed(new Runnable() {
					public void run() {
						Intent explicit = new Intent();
						explicit.setClassName("com.improvider",
								"com.improvider.Main");
						// On rajoute le code de la session

						explicit.putExtra("numeroSession", 3);

						startActivity(explicit);

					}

				}, 30);

			}
		});

		boutonAcoustic = (Button) findViewById(R.id.boutonAcoustic);
		boutonAcoustic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toast = Toast.makeText(context, R.string.chargement,
						Toast.LENGTH_LONG);
				toast.show();
				if (BuildMode.DEBUG) {
				sendToTrackerSession("Sad Melodic");
				}
				Handler lHandler = new Handler();

				lHandler.postDelayed(new Runnable() {
					public void run() {

						Intent explicit = new Intent();
						explicit.setClassName("com.improvider",
								"com.improvider.Main");
						// On rajoute le code de la session

						explicit.putExtra("numeroSession", 4);

						startActivity(explicit);

					}

				}, 30);

			}
		});

		boutonHardRock = (Button) findViewById(R.id.boutonHardRock);
		boutonHardRock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toast = Toast.makeText(context, R.string.chargement,
						Toast.LENGTH_LONG);
				toast.show();
				if (BuildMode.DEBUG) {
				sendToTrackerSession("Hard Rock");
				}
				Handler lHandler = new Handler();

				lHandler.postDelayed(new Runnable() {
					public void run() {

						Intent explicit = new Intent();
						explicit.setClassName("com.improvider",
								"com.improvider.Main");
						// On rajoute le code de la session

						explicit.putExtra("numeroSession", 5);

						startActivity(explicit);

					}

				}, 30);

			}
		});

		boutonChoixRetour = (Button) findViewById(R.id.bouton_choix_retour);
		boutonChoixRetour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
				Intent explicit = new Intent();
				explicit.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				explicit.setClassName("com.improvider",
						"com.improvider.Improvider");
				startActivity(explicit);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choix_accompagnement, menu);
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
		if (BuildMode.DEBUG) {
		EasyTracker.getInstance(this).activityStop(this); 
		}// Add this method.
	}

	public void onPause() {
		super.onPause();
		if (toast != null) {

			toast.cancel();
		}

	}

	 public void onStart() {
		    super.onStart();
		    if (BuildMode.DEBUG) {
		    EasyTracker.getInstance(this).activityStart(this);
		    }// Add this method.
		  }
	 
	 private void sendToTrackerSession(String action) {
		// May return null if a EasyTracker has not yet been initialized with a
		  // property ID.
		 if (BuildMode.DEBUG) {
		  EasyTracker easyTracker = EasyTracker.getInstance(this);

		  // MapBuilder.createEvent().build() returns a Map of event fields and values
		  // that are set and sent with the hit.
		  easyTracker.send(MapBuilder
		      .createEvent("Session choisie",     // Event category (required)
		                   action,  // Event action (required)
		                   action,   // Event label
		                   null)            // Event value
		      .build()
		  );
	 }
	 }
}
