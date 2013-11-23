package com.improvider;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost;

public class Main extends Activity {

	// Attributs liés à l'accompagnement
	public static int niveausonore = 50;
	private SoundPool soundPool;
	private int C2piano, Cd2piano, D2piano, Dd2piano, E2piano, F2piano,
			Fd2piano, G2piano, Gd2piano, A2piano, Ad2piano, B2piano, C3piano,
			Cd3piano, D3piano, Dd3piano, E3piano, F3piano, Fd3piano, G3piano,
			Gd3piano, A3piano, Ad3piano, B3piano, C4piano, Cd4piano, D4piano,
			Dd4piano, E4piano, F4piano, Fd4piano, G4piano, Gd4piano, A4piano,
			Ad4piano, B4piano;
	private Musique gestionMusique;
	private int Adresse;
	private String Auteur;
	private final int volumeAccompagnementInitial = 65;
	int volumePlayer;
	public float volume;

	// Gestion du Piano
	private boolean[] Gamme;
	private int tonique;
	public Piano piano;
	public PianoHorizontalScrollView scroller;
	public ImageScroller imageScroller;
	public double volumePiano = 0.25;
	public double volumeProportion = volumePiano / 0.5;
	public int screenWidth;
	public int screenHeight;
	private boolean premierScroll = true;
	private AudioManager audioManager = null;
	// Boutons de réglages
	TabHost onglets;

	public SeekBar volumeBar;
	public SeekBar avancementBar;
	public SeekBar volumePianoBar;
	private Button boutonMorceauRetour;
	private Button boutonReglageRetour;
	public boolean sustain = true;

	private ImageButton sustainButton;
	private TextView sustainText;
	private ImageButton sustainInfoButton;
	public TextView tempsMax;
	public TextView tempsMin;
	public SeekBar nbreBlanchesVisiblesBar;

	private CharSequence duree;

	private int widthScreen;

	private int heightScreen;
	private float density;
	private float dpHeight;
	private float dpWidth;
	private float diagonalInch;
	final Context context = this;

	public void onCreate(Bundle icicle)

	{
		super.onCreate(icicle);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

		/*
		 * int parentWidth = MeasureSpec.getSize(UNSPECIFIED); FrameLayout
		 * _rootLayout = (FrameLayout) findViewById(R.id.tabcontent);
		 * RelativeLayout.LayoutParams _rootLayoutParams = new
		 * RelativeLayout.LayoutParams(_rootLayout.getWidth(),
		 * _rootLayout.getHeight()); _rootLayoutParams.setMargins(300, 0, 300,
		 * 0); _rootLayout.setLayoutParams(_rootLayoutParams);
		 */

		// On récupère les infos de l'Intent envoyés par ChoixAccompagnement.
		Bundle extras = getIntent().getExtras();
		Adresse = extras.getInt("Adresse");
		Gamme = extras.getBooleanArray("Gamme");
		tonique = extras.getInt("Tonique");
		Auteur = extras.getString("name");

		// 2 Boutons de retour
		boutonMorceauRetour = (Button) findViewById(R.id.bouton_morceau_retour);
		boutonMorceauRetour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
				Intent explicit = new Intent();
				explicit.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				explicit.setClassName("com.improvider",
						"com.improvider.ChoixAccompagnement");
				startActivity(explicit);

			}
		});

		boutonReglageRetour = (Button) findViewById(R.id.bouton_reglages_retour);
		boutonReglageRetour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
				Intent explicit = new Intent();
				explicit.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				explicit.setClassName("com.improvider",
						"com.improvider.ChoixAccompagnement");
				startActivity(explicit);
			}
		});

		// Création des deux barres de volume
		volumeBar = (SeekBar) findViewById(R.id.volume_accompagnement_bar);
		volumeBar.setProgress(volumeAccompagnementInitial);
		volumeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				setVolumeGestion(progress);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		}

		);

		volumePianoBar = (SeekBar) findViewById(R.id.volume_piano_bar);
		volumePianoBar.setProgress(30);
		volumePianoBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						;
						setVolumePiano(progress);

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

				}

				);

		// Bouton Sustain
		sustainButton = (ImageButton) findViewById(R.id.sustain_button);

		sustainButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				piano.setSustain(!piano.getSustain());
				if (piano.getSustain()) {

					sustainButton.setBackgroundResource(R.drawable.checked);
				}

				else {
					sustainButton.setBackgroundResource(R.drawable.notchecked);

				}
			}
		});
		/*
		 * sustainText=(TextView) findViewById(R.id.sustain_text);
		 * sustainText.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) {
		 * 
		 * piano.setSustain(!piano.getSustain()); if (piano.getSustain()) {
		 * 
		 * sustainButton.setBackgroundResource(R.drawable.checked); }
		 * 
		 * else { sustainButton.setBackgroundResource(R.drawable.notchecked);
		 * 
		 * } } });
		 */
		sustainInfoButton = (ImageButton) findViewById(R.id.bouton_sustain_info);
		sustainInfoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set title
				alertDialogBuilder.setTitle(R.string.sustain);

				// set dialog message
				alertDialogBuilder.setMessage(R.string.sustain_explanation)
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		});

		// On passe aux choses sérieuses

		// On associe les boutons materiels au controle du volume de
		// l'application
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		/*
		 * Chargement des sons nécessaires
		 */
		soundPool = new SoundPool(24, AudioManager.STREAM_MUSIC, 0);

		// Première Octave
		C2piano = soundPool.load(this, R.raw.c2piano, 1);
		Cd2piano = soundPool.load(this, R.raw.c2dpiano, 1);
		D2piano = soundPool.load(this, R.raw.d2piano, 1);
		Dd2piano = soundPool.load(this, R.raw.dd2piano, 1);
		E2piano = soundPool.load(this, R.raw.e2piano, 1);
		F2piano = soundPool.load(this, R.raw.f2piano, 1);
		Fd2piano = soundPool.load(this, R.raw.fd2piano, 1);
		G2piano = soundPool.load(this, R.raw.g2piano, 1);
		Gd2piano = soundPool.load(this, R.raw.gd2piano, 1);
		A2piano = soundPool.load(this, R.raw.a2piano, 1);
		Ad2piano = soundPool.load(this, R.raw.ad2piano, 1);
		B2piano = soundPool.load(this, R.raw.b2piano, 1);

		// Seconde Octave
		C3piano = soundPool.load(this, R.raw.c3piano, 1);
		Cd3piano = soundPool.load(this, R.raw.cd3piano, 1);
		D3piano = soundPool.load(this, R.raw.d3piano, 1);
		Dd3piano = soundPool.load(this, R.raw.dd3piano, 1);
		E3piano = soundPool.load(this, R.raw.e3piano, 1);
		F3piano = soundPool.load(this, R.raw.f3piano, 1);
		Fd3piano = soundPool.load(this, R.raw.fd3piano, 1);
		G3piano = soundPool.load(this, R.raw.g3piano, 1);
		Gd3piano = soundPool.load(this, R.raw.gd3piano, 1);
		A3piano = soundPool.load(this, R.raw.a3piano, 1);
		Ad3piano = soundPool.load(this, R.raw.ad3piano, 1);
		B3piano = soundPool.load(this, R.raw.b3piano, 1);

		// Troisi�me octave
		C4piano = soundPool.load(this, R.raw.c4piano, 1);
		Cd4piano = soundPool.load(this, R.raw.cd4piano, 1);
		D4piano = soundPool.load(this, R.raw.d4piano, 1);
		Dd4piano = soundPool.load(this, R.raw.dd4piano, 1);
		E4piano = soundPool.load(this, R.raw.e4piano, 1);
		F4piano = soundPool.load(this, R.raw.f4piano, 1);
		Fd4piano = soundPool.load(this, R.raw.fd4piano, 1);
		G4piano = soundPool.load(this, R.raw.g4piano, 1);
		Gd4piano = soundPool.load(this, R.raw.gd4piano, 1);
		A4piano = soundPool.load(this, R.raw.a4piano, 1);
		Ad4piano = soundPool.load(this, R.raw.ad4piano, 1);
		B4piano = soundPool.load(this, R.raw.b4piano, 1);

		/*
		 * On envoie les sons à l'instance de la classe Piano
		 */
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

		piano = (Piano) findViewById(R.id.tab_piano);
		piano.setGamme(Gamme);
		piano.setScreenWidth(screenWidth);
		piano.setTonique(tonique);

		int a = piano.getHeight();

		// Recuperation du volume
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = actualVolume / maxVolume;
		float volumeffectif = (float) (volumePiano * volume);

		// Envoi des informations à la classe Piano

		piano.recupererSon(soundPool, new int[] { C2piano, Cd2piano, D2piano,
				Dd2piano, E2piano, F2piano, Fd2piano, G2piano, Gd2piano,
				A2piano, Ad2piano, B2piano, C3piano, Cd3piano, D3piano,
				Dd3piano, E3piano, F3piano, Fd3piano, G3piano, Gd3piano,
				A3piano, Ad3piano, B3piano, C4piano, Cd4piano, D4piano,
				Dd4piano, E4piano, F4piano, Fd4piano, G4piano, Gd4piano,
				A4piano, Ad4piano, B4piano }, volumeffectif);

		piano.setVolume(volumeffectif);

		// Cr�ation de l'image Scroller

		scroller = (PianoHorizontalScrollView) findViewById(R.id.scroller);

		imageScroller = (ImageScroller) findViewById(R.id.image_scroller);
		imageScroller.setPiano(piano);
		imageScroller.setPianoHorizontalScrollView(scroller);
		imageScroller.setGamme(Gamme);
		imageScroller.setTonique(tonique);
		scroller.setImageScroller(imageScroller);

		// Barre de réglages du nombre de touches apparaissant à l'écran

		nbreBlanchesVisiblesBar = (SeekBar) findViewById(R.id.nbre_blanches_visibles_bar);
		nbreBlanchesVisiblesBar.setProgress(7);

		nbreBlanchesVisiblesBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						if (fromUser) {

							double nouvelleProp = (double) 7 / (progress + 3);
							piano.setProportionPianoHorizontale(nouvelleProp);
						}
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

				}

				);

		/*
		 * Classe de gestion de la musique
		 */
		gestionMusique = new Musique(findViewById(R.id.tab1), findViewById(
				R.id.tab1).getContext());
		// On utilise les informations de l'intent.
		gestionMusique.setPlayer(Adresse);
		gestionMusique.setAuteur(Auteur);
		gestionMusique.setVolume(volumeAccompagnementInitial);

		// Barre d'avancement
		int dureems = gestionMusique.getDuration();

		int minutes = (int) dureems / 60000;
		int secondes = (int) (dureems - minutes * 60000) / 1000;

		if (secondes > 10) {
			duree = " " + minutes + ":" + secondes + "    ";
		}

		else {
			duree = " " + minutes + ":0" + secondes + "    ";
		}
		tempsMax = (TextView) findViewById(R.id.avancement_max);
		tempsMax.setText(duree);
		tempsMin = (TextView) findViewById(R.id.avancement_min);

		avancementBar = (SeekBar) findViewById(R.id.avancement_bar);
		avancementBar.setMax(getDuration());
		avancementBar.setProgress(getPositionGestion());

		// Actualisation de la dite barre

		TimerTask actualisation = new TimerTask() {

			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						avancementBar.setProgress(getPositionGestion());

						int dureems = getPositionGestion();
						int minutes = (int) dureems / 60000;
						int secondes = (int) (dureems - minutes * 60000) / 1000;
						if (secondes >= 10) {
							String duree = "   " + minutes + ":" + secondes
									+ " ";
							setTempsMin(duree);
						} else {
							String duree = "   " + minutes + ":0" + secondes
									+ " ";
							setTempsMin(duree);
						}

					}
				});
			}

		};
		Timer t = new Timer();

		t.schedule(actualisation, (long) 1000, (long) 1000);

		// Changement de l'avancement selon l'utilisateur

		avancementBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					setPositionGestion(progress);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		}

		);

		/*
		 * Modification des onglets
		 */

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		widthScreen = metrics.widthPixels;
		heightScreen = metrics.heightPixels;
		density = getResources().getDisplayMetrics().density;

		dpHeight = heightScreen / density;
		dpWidth = widthScreen / density;

		diagonalInch = (float) Math.sqrt(dpHeight * dpHeight + dpWidth
				* dpWidth) / 160;

		imageScroller.setScreenWidth(widthScreen);

		onglets = (TabHost) findViewById(R.id.tabhost);
		onglets.setup();

		// Onglet de selection d'un morceau
		TabHost.TabSpec ongletMorceau = onglets.newTabSpec("ongletMorceau");

		ongletMorceau.setContent(R.id.tab1);
		String acc = getString(R.string.accompagnement);
		ongletMorceau.setIndicator(acc);
		onglets.addTab(ongletMorceau);
		onglets.getTabWidget().getChildAt(0).getLayoutParams().height = (int) (heightScreen * 0.11);
		onglets.getTabWidget().getChildAt(0)
				.setBackgroundResource(R.drawable.tab_bg_selector);
		TextView tvb = (TextView) onglets.getTabWidget().getChildAt(0)
				.findViewById(android.R.id.title);

		tvb.setTextColor(Color.WHITE);
		tvb.setTextSize(0, 30);
		// Onglet "Piano"
		TabHost.TabSpec ongletPiano = onglets.newTabSpec("ongletPiano");

		ongletPiano.setContent(R.id.tab2);
		String pia = getString(R.string.piano);

		ongletPiano.setIndicator(pia);
		onglets.addTab(ongletPiano);
		onglets.getTabWidget().getChildAt(1).getLayoutParams().height = (int) (heightScreen * 0.11);

		onglets.getTabWidget().getChildAt(1)
				.setBackgroundResource(R.drawable.tab_bg_selector);

		onglets.getTabWidget().getChildAt(1)
				.setOnTouchListener(new View.OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						if (!premierScroll) {
							int a = piano.getHeight();

							return false;

						}

						else {
							int a = piano.getHeight();

							Handler lHandler = new Handler();

							lHandler.postDelayed(new Runnable() {
								public void run() {
									PianoHorizontalScrollView scroler = (PianoHorizontalScrollView) findViewById(R.id.scroller);
									int a = scroler.getWidth();

									int positionToScroll = piano
											.positionTouche(tonique);
									scroler.customSmoothScrollTo(
											positionToScroll, 0);

									getImageScroller().setX1(positionToScroll);
									getImageScroller().invalidate();
								}

							}, 100);

							premierScroll = false;
							return false;
						}

					}
				});

		// Onglet "Réglages"
		TabHost.TabSpec ongletReglages = onglets.newTabSpec("ongletReglages");

		ongletReglages.setContent(R.id.tab3);
		String regl = getString(R.string.reglages);
		ongletReglages.setIndicator(regl);
		onglets.addTab(ongletReglages);
		onglets.getTabWidget().getChildAt(2).getLayoutParams().height = (int) (heightScreen * 0.11);

		for (int i = 0; i < onglets.getTabWidget().getChildCount(); i++) {
			TextView tv = (TextView) onglets.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title);

			setTextSizeOnglets(tv);
			onglets.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.tab_bg_selector);
		}
		setDimensionsOngletPiano();

	}

	/*
	 * Gestion du cycle de vie de l'application
	 */

	public void onDestroy() {
		super.onDestroy();
		gestionMusique.couperMusique();
		soundPool.release();

	}

	public void onStop() {
		super.onStop();
		gestionMusique.mettreEnPause();
		soundPool.autoPause();

	}

	public void onPause() {
		super.onPause();
		gestionMusique.mettreEnPause();
		soundPool.autoPause();

	}

	public void onResume() {
		super.onResume();
		soundPool.autoResume();

	}

	// GETTERS

	public float getVolumePiano() {
		return (float) volumePiano;
	}

	public int getAdresse() {
		return Adresse;
	}

	public Musique getGestionMusique() {
		return gestionMusique;
	}

	public void setPositionGestion(int i) {
		gestionMusique.setPosition(i);

	}

	public int getPositionGestion() {
		return gestionMusique.getPosition();

	}

	public int getDuration() {
		return gestionMusique.getDuration();

	}

	// Setters

	public void setVolumeGestion(int i) {
		gestionMusique.setVolume(i);
	}

	public void setAdresse(int adresse) {
		Adresse = adresse;
	}

	public boolean[] getGamme() {
		return Gamme;
	}

	public void setGamme(boolean[] gamme) {
		Gamme = gamme;
	}

	public void setVolumePiano(int i) {
		double j = (double) i;
		volumePiano = (j / 100);

		volumeProportion = volumePiano / 0.5;
		piano.setVolume((float) volumePiano * volume);

	}

	public void setTempsMin(String text) {
		tempsMin.setText(text);
	}

	public ImageScroller getImageScroller() {
		return this.imageScroller;
	}

	private void setDimensionsOngletPiano() {

		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
			for (int i = 0; i < 3; i++) {
				onglets.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (heightScreen * 0.15);
			}
			piano.setProportionPianoVerticale(0.65);
			imageScroller.setProportionPianoVerticale(0.16);
			double nouvelleProp = (double) 7 / 6;
			nbreBlanchesVisiblesBar.setProgress(3);
			piano.setProportionPianoHorizontale(nouvelleProp);
		}

		else {
			if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
				for (int i = 0; i < 3; i++) {
					onglets.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (heightScreen * 0.11);
				}
				piano.setProportionPianoVerticale(0.72);
				imageScroller.setProportionPianoVerticale(0.13);
				double nouvelleProp = (double) 7 / 8;
				nbreBlanchesVisiblesBar.setProgress(5);
				piano.setProportionPianoHorizontale(nouvelleProp);
			}

			else {
				if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
					for (int i = 0; i < 3; i++) {
						onglets.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (heightScreen * 0.10);
					}
					piano.setProportionPianoVerticale(0.74);
					imageScroller.setProportionPianoVerticale(0.11);
					double nouvelleProp = (double) 7 / 10;
					nbreBlanchesVisiblesBar.setProgress(7);
					piano.setProportionPianoHorizontale(nouvelleProp);

				}

				else {

					if (android.os.Build.VERSION.SDK_INT >= 13) {

						if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {

							for (int i = 0; i < 3; i++) {
								onglets.getTabWidget().getChildAt(i)
										.getLayoutParams().height = (int) (heightScreen * 0.09);
							}
							piano.setProportionPianoVerticale(0.76);
							imageScroller.setProportionPianoVerticale(0.10);
							double nouvelleProp = (double) 7 / 12;
							nbreBlanchesVisiblesBar.setProgress(7);
							piano.setProportionPianoHorizontale(nouvelleProp);
						} else {
							for (int i = 0; i < 3; i++) {
								onglets.getTabWidget().getChildAt(i)
										.getLayoutParams().height = (int) (heightScreen * 0.09);
							}
							piano.setProportionPianoVerticale(0.76);
							imageScroller.setProportionPianoVerticale(0.12);
							nbreBlanchesVisiblesBar.setProgress(7);
							double nouvelleProp = (double) 7 / 10;

							piano.setProportionPianoHorizontale(nouvelleProp);
						}
					}

				}

			}
		}

		imageScroller
				.setProportionInitiale(this.piano.proportionPianoHorizontale);
	}

	private void setTextSizeOnglets(TextView textview) {
		int sizeTexte = 18;
		textview.setTextSize(sizeTexte);
		textview.setGravity(0x00000011);
		textview.setTextColor(Color.BLACK);

		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
			sizeTexte = 14;
			textview.setTextSize(sizeTexte);

		}

		else {
			if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
				sizeTexte = 18;
				textview.setTextSize(sizeTexte);

			}

			else {
				if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
					sizeTexte = 20;
					textview.setTextSize(sizeTexte);

				}

				else {
					if (android.os.Build.VERSION.SDK_INT >= 13) {

						if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
							sizeTexte = 24;
							textview.setTextSize(sizeTexte);

						} else {
							sizeTexte = 21;
							textview.setTextSize(sizeTexte);

						}
					}

				}

			}
		}
	}

}
