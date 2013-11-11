package com.improvider;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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

				// On passe � l'activit� ChoixAccompagnement
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
				// On passe � l'activit� ChoixAccompagnement
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

}
