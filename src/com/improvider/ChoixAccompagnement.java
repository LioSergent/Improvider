package com.improvider;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.improvider.R;

public class ChoixAccompagnement extends Activity {
	// Les 5 boutons correspondants aux morceaux
	private Button boutonBarBlues;
	private Button boutonBluesSoul;
	private Button boutonHipHop;
	private Button boutonAcoustic;
	private Button boutonHardRock;
	private Button boutonChoixRetour;
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
				toast = Toast.makeText(context, R.string.chargement, 20000);
				toast.show();
				// on cr�e un intent pour passer � l'activit� Improvider
				//Le handler sert a retarder le lancement, sinon le toast a pas le temps de show
				Handler lHandler = new Handler();

				lHandler.postDelayed(new Runnable() {
					public void run() {
						Intent explicit = new Intent();
						// On sp�cifie la prochaine activit�
						explicit.setClassName("com.improvider",
								"com.improvider.Main");
						// On rajoute les informations permettant de lancer la
						// bonne
						// session de jeu.
						explicit.putExtra("Adresse", R.raw.barbluesaminor);
						boolean[] value = { true, true, true, false, true,
								true, false, true, false, false, false, false,
								false, false };
						explicit.putExtra("Gamme", value);
						explicit.putExtra("name", "Bar Blues");
						explicit.putExtra("Tonique", 5);
						startActivity(explicit);

					}

				}, 30);

			}
		});
		boutonBluesSoul = (Button) findViewById(R.id.boutonBluesSoul);
		boutonBluesSoul.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
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
								true, true, false, false, false, false, false,
								false, false };
						explicit.putExtra("Gamme", value);
						explicit.putExtra("name",
								"Blues Soul Em");
						explicit.putExtra("Tonique", 2);
						startActivity(explicit);

					}

				}, 30);

			}
		});

		boutonHipHop = (Button) findViewById(R.id.boutonHipHop);
		boutonHipHop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toast = Toast.makeText(context, R.string.chargement, 20000);
				toast.show();

				Handler lHandler = new Handler();

				lHandler.postDelayed(new Runnable() {
					public void run() {
						Intent explicit = new Intent();
						explicit.setClassName("com.improvider",
								"com.improvider.Main");
						explicit.putExtra("Adresse",
								R.raw.freestylerapbeatinstrumentalincminor);
						boolean[] value = { true, true, false, true, true,
								false, false, true, false, true, false, false,
								false, true, false };
						explicit.putExtra("Gamme", value);
						explicit.putExtra("name",
								"Hip pop style Cm by Tom Bailey ");
						explicit.putExtra("Tonique", 0);
						startActivity(explicit);

					}

				}, 30);

			}
		});

		boutonAcoustic = (Button) findViewById(R.id.boutonAcoustic);
		boutonAcoustic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toast = Toast.makeText(context, R.string.chargement, 20000);
				toast.show();

				Handler lHandler = new Handler();

				lHandler.postDelayed(new Runnable() {
					public void run() {

						Intent explicit = new Intent();
						explicit.setClassName("com.improvider",
								"com.improvider.Main");
						explicit.putExtra("Adresse",
								R.raw.acousticpopguitarbackingtrackinbmajor);
						boolean[] value = { false, false, false, false, false,
								false, true, false, true, true, false, true,
								true, false, false };
						explicit.putExtra("Gamme", value);
						explicit.putExtra("name",
								"Acoustic Guitar BM");
						explicit.putExtra("Tonique", 6);
						startActivity(explicit);

					}

				}, 30);

			}
		});

		boutonHardRock = (Button) findViewById(R.id.boutonHardRock);
		boutonHardRock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				toast = Toast.makeText(context, R.string.chargement, 20000);
				toast.show();

				Handler lHandler = new Handler();

				lHandler.postDelayed(new Runnable() {
					public void run() {

						Intent explicit = new Intent();
						explicit.setClassName("com.improvider",
								"com.improvider.Main");
						explicit.putExtra("Adresse",
								R.raw.hardrockguitarbackingtrackineminor);
						boolean[] value = { false, true, true, false, true,
								true, true, false, false, false, false, false,
								false, false };
						explicit.putExtra("Gamme", value);
						explicit.putExtra("name", "Hard Rock Em");
						explicit.putExtra("Tonique", 2);
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
		if (toast!=null) {
		
		toast.cancel();
	}
	}

	public void onStop() {
		super.onStop();
		if (toast!=null) {
			
			toast.cancel();
		}
	}

	public void onPause() {
		super.onPause();
		if (toast!=null) {
			
			toast.cancel();
		}

	}
}
