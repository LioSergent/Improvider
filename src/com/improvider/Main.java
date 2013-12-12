package com.improvider;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.media.AudioManager;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost;

/**
 * Activité principale. C'est elle qui gère les 3 onglets et qui va instancier
 * les boutons et charger les données audio pour les envoyer vers Piano ,
 * Musique...
 * 
 * @author Lionel
 * 
 */
public class Main extends Activity implements Constants {

	// Attributs

	// Attributs liés à l'accompagnement
	public static int niveausonore = 50;

	private Musique gestionMusique;
	private int Adresse;
	private final int volumeAccompagnementInitial = 78;
	int volumePlayer;
	public float volume;

	// Gestion du son du Piano
	public Piano piano;
	private AudioManager audioManager = null;

	// Gestion graphique et dynamique du Piano
	private boolean[] CurrentGamme;
	private Session session;
	private int tonique;
	public PianoHorizontalScrollView scroller;
	public ImageScroller imageScroller;
	private boolean premierScroll = true;
	private int numeroSession;
	private String nameSession;

	// Navigation
	TabHost onglets;
	private Button boutonMorceauRetour;
	private Button boutonReglageRetour;

	// Boutons de réglages et d'information

	public SeekBar volumeBar;
	public SeekBar avancementBar;
	public SeekBar volumePianoBar;
	private ImageButton sustainButton;
	private ImageButton sustainInfoButton;
	public TextView tempsMax;
	public TextView tempsMin;
	public SeekBar nbreBlanchesVisiblesBar;
	private CharSequence duree;
	public Button chooseInstrumentButton;

	// Attributs de dimensions
	private int widthScreen;
	private int heightScreen;
	private float density;
	private float dpHeight;
	private float dpWidth;
	private float diagonalInch;

	// Contexte
	final Context context = this;

	// Méthodes
	public void onCreate(Bundle icicle)

	{

		// Création de la fenêtre
		super.onCreate(icicle);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		// On rÃ©cupÃ¨re les infos de l'Intent envoyÃ©s par ChoixAccompagnement.
		Bundle extras = getIntent().getExtras();
		numeroSession = extras.getInt("numeroSession");

		// Grâce au code de session, on charge la dite session.
		chargeSession(numeroSession);

		// Récupération de données graphiques
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		widthScreen = metrics.widthPixels;
		heightScreen = metrics.heightPixels;
		density = getResources().getDisplayMetrics().density;
		dpHeight = heightScreen / density;
		dpWidth = widthScreen / density;

		diagonalInch = (float) Math.sqrt(dpHeight * dpHeight + dpWidth
				* dpWidth) / 160;

		// Chargement des objets liées au son

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

		// On associe les boutons materiels au controle du volume de
		// l'application
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// Récupération du piano
		piano = (Piano) findViewById(R.id.tab_piano);
		piano.setGamme(CurrentGamme);
		piano.setScreenWidth(widthScreen);
		piano.setTonique(tonique);

		// Recuperation du volume pour le piano

		// Envoi des informations Ã  la classe Piano

		/*
		 * Création de l'objet de gestion de l'accompagnement
		 */
		gestionMusique = new Musique(findViewById(R.id.tab1), findViewById(
				R.id.tab1).getContext());
		// On utilise les informations de l'intent.
		gestionMusique.setPlayer(Adresse);

		gestionMusique.setVolume(volumeAccompagnementInitial);

		// Les boutons

		// L'affichage du nom de la session

		TextView textViewNameSession = (TextView) findViewById(R.id.name_session);
		textViewNameSession.setText(nameSession);

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

		// CrÃ©ation des deux barres de volume
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

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

		}

		);

		volumePianoBar = (SeekBar) findViewById(R.id.volume_piano_bar);
		volumePianoBar.setProgress(10);
		piano.instrument.setVolume(volumePianoBar.getProgress());
		volumePianoBar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						;
						piano.instrument.setVolume(progress);

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}

				}

				);

		// Choix de l'instrument

		chooseInstrumentButton = (Button) findViewById(R.id.button_choose_instrument);
		chooseInstrumentButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				final CharSequence[] items = {
						getResources().getString(R.string.piano),
						getResources().getString(R.string.guitar),
						getResources().getString(R.string.organ) };
				alertDialogBuilder.setTitle(R.string.choose_instrument);

				alertDialogBuilder.setItems(items,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								switch (which) {
								case 0:
									Instrument a = new InstruPiano(context);
									piano.setInstrument(a);
									piano.instrument.setVolume(volumePianoBar
											.getProgress());
									Toast toast = Toast.makeText(context,
											R.string.pianoloaded,
											Toast.LENGTH_SHORT);
									toast.show();
									break;
								case 1:
									Instrument b = new InstruGuitar(context);
									piano.setInstrument(b);
									piano.instrument.setVolume(volumePianoBar
											.getProgress());
									Toast toast1 = Toast.makeText(context,
											R.string.guitarloaded,
											Toast.LENGTH_SHORT);
									toast1.show();
									break;
								case 2:
									Instrument c = new InstruOrgan(context);
									piano.setInstrument(c);
									piano.instrument.setVolume(volumePianoBar
											.getProgress());
									Toast toast2 = Toast.makeText(context,
											R.string.organloaded,
											Toast.LENGTH_SHORT);
									toast2.show();
									break;
								}
							}
						});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});

		// Bouton Sustain
		sustainButton = (ImageButton) findViewById(R.id.sustain_button);

		sustainButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				piano.instrument.setSustain(!piano.instrument.getSustain());
				if (piano.instrument.getSustain()) {

					sustainButton.setBackgroundResource(R.drawable.checked);
				}

				else {
					sustainButton.setBackgroundResource(R.drawable.notchecked);

				}
			}
		});

		// Le bouton d'info sur le sustain
		sustainInfoButton = (ImageButton) findViewById(R.id.bouton_sustain_info);
		sustainInfoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				alertDialogBuilder.setTitle(R.string.sustain);
				alertDialogBuilder.setMessage(R.string.sustain_explanation)
						.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});

		// Barre de rÃ©glages du nombre de touches apparaissant Ã  l'Ã©cran

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
							imageScroller.invalidate();
						}
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}

				}

				);

		// Barre d'avancement
		// Recherche de la fin
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

		// Création de la barre

		avancementBar = (SeekBar) findViewById(R.id.avancement_bar);
		avancementBar.setMax(getDuration());
		avancementBar.setProgress(getPositionGestion());

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

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

		}

		);

		// Actualisation des barres via Timer: avancementBar et
		// nbreBlanchesVisiblesBar

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
						int progress = (int) Math.ceil(7 / (piano
								.getProportionPianoHorizontale()) - 3);
						nbreBlanchesVisiblesBar.setProgress(progress);
					}
				});
			}

		};
		Timer t = new Timer();

		t.schedule(actualisation, (long) 1500, (long) 200);

		// Création des éléments graphiques plus complexes

		/*
		 * Création du scroller et de l'imageScroller, don au piano de
		 * l'imageScroller, du à un problème de non-actualisation de ce dernier
		 * lors du scroll automatique
		 */

		scroller = (PianoHorizontalScrollView) findViewById(R.id.scroller);
		imageScroller = (ImageScroller) findViewById(R.id.image_scroller);
		imageScroller.setPiano(piano);
		imageScroller.setPianoHorizontalScrollView(scroller);
		imageScroller.setGamme(CurrentGamme);
		imageScroller.setTonique(tonique);

		/*
		 * Modification des onglets
		 */

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
		String pia = getString(R.string.keyboard);

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
							return false;

						}

						else {

							PianoHorizontalScrollView scroler = (PianoHorizontalScrollView) findViewById(R.id.scroller);

							int positionToScroll = piano
									.positionTouche(tonique);
							scroler.customSmoothScrollTo(positionToScroll, 0);
							getImageScroller().setX1(positionToScroll);
							getImageScroller().invalidate();

							premierScroll = false;
							return false;
						}

					}
				});

		// Onglet "RÃ©glages"
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

		// Relayoutage de quelques éléments perturbateurs

		setDimensionsOngletPiano();
		reSizePlay(diagonalInch);

	}

	// Méthodes diverses

	/*
	 * Gestion du cycle de vie de l'application
	 */

	public void onDestroy() {
		super.onDestroy();
		gestionMusique.couperMusique();
		piano.instrument.release();

	}

	public void onStop() {
		super.onStop();
		gestionMusique.mettreEnPause();
		piano.instrument.soundPool.autoPause();
	}

	public void onPause() {
		super.onPause();
		gestionMusique.mettreEnPause();
		piano.instrument.soundPool.autoPause();
		;
	}

	public void onResume() {
		super.onResume();
		piano.instrument.soundPool.autoResume();

	}

	/**
	 * Choisit la session à charger en fonction du code donné par
	 * ChoixAccompagnement. Moche mais évite de passer des objets entre les
	 * activités.
	 * 
	 * @param i
	 */
	private void chargeSession(int i) {
		switch (i) {
		case 1:
			this.session = barBluesAm;
			break;
		case 2:
			this.session = bluesSoulEm;
			break;
		case 3:
			this.session = hipHopCm;
			break;
		case 4:
			this.session = sadMelodicBm;
			break;
		case 5:
			this.session = hardRockEm;
			break;
		default:
			this.session = bluesSoulEm;
		}
		this.Adresse = session.getAdresse();
		this.CurrentGamme = session.getScale()[0].getUsedValue();
		this.tonique = session.getScale()[0].getTonique();
		this.nameSession = session.getNom();
	}

	// Re-layout programaticly (f(diagonale, screen type))

	private void reSizePlay(float diagonalInch) {

		ImageButton buttonPlay = (ImageButton) findViewById(R.id.boutonPlay);
		int maxWidth = (int) (12 * diagonalInch + 11 * diagonalInch
				* diagonalInch - 110);
		buttonPlay.setMaxWidth(maxWidth);
		buttonPlay.setMaxHeight(maxWidth);

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

	// Setters d'attributs

	public void setVolumeGestion(int i) {
		gestionMusique.setVolume(i);
	}

	public void setAdresse(int adresse) {
		Adresse = adresse;
	}

	public boolean[] getGamme() {
		return CurrentGamme;
	}

	public void setGamme(boolean[] gamme) {
		CurrentGamme = gamme;
	}

	public void setTempsMin(String text) {
		tempsMin.setText(text);
	}

	// GETTERS

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

	public ImageScroller getImageScroller() {
		return this.imageScroller;
	}
}
