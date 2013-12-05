package com.improvider;

import java.util.Hashtable;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Classe de type View qui gère le dessin du piano, les évènements graphiques et
 * sonore qui arrivent dessus. Elle se sert d'informations récupérées par
 * l'activité Main, et est suportée graphiquement par un
 * PianoHorizontalScrollView pour gérer le scroll (et un ImageScroller pour
 * l'image de ce scroller).
 * 
 * @author Lionel
 * 
 */
public class Piano extends View {

	/*
	 * Constantes de la classe
	 */

	private final static double proportionToucheNoireHauteur = 0.75;
	private final static double proportionToucheNoireLargeur = 0.32;
	private final static int nbreOctave = 3;

	// Proportions du piano, par rapport à la taille de l'écran
	public double proportionPianoVerticale = 0.75;
	public double proportionPianoHorizontale = 0.875;

	/*
	 * Id du pointeur, numero de la touche (en commencant Ã  0) (+ 10 si noire)
	 */
	Hashtable<Integer, Integer> positionPointeurs = new Hashtable<Integer, Integer>();

	// Map associant numéro de soundclouds et touches (pour arrêter le son).
	// Numéro de soundCloud= entier croissant dans l'ordre de lancement.(1
	// pour
	// le premier soundcloud, 2 pour le suivant...)

	Hashtable<Integer, Integer> soundids = new Hashtable<Integer, Integer>();

	// Taille de l'écran
	int screenWidth;
	int heightScreen;
	int widthScreen;

	// Taille des éléments graphiques
	int largeur, hauteur, largeurTotale;
	int largeurToucheBlanche;
	int largeurToucheNoire;
	int hauteurToucheNoire;
	int hauteurToucheBlanche;

	Context contexte;

	private boolean[] gamme;
	private int tonique = 0;

	// Tableaux d'état des touches
	boolean[] tabEtatTouchesBlanches = new boolean[7 * nbreOctave];
	boolean[] tabEtatTouchesNoires = new boolean[7 * nbreOctave];

	// Valeurs de stockage pour le onTouchEvent
	int x, y;

	// Avant de dessiner, on revérifie qu'on a bien initié.
	boolean init = false;

	/*
	 * Les pinceaux
	 */
	private Paint pinceauToucheNoireAppuye, pinceauToucheNoireRelache;
	private Paint pinceauToucheBlancheAppuye;
	private Paint pinceauToucheNoireJouable;
	private Paint pinceauToucheBlancheJouable;
	private Paint pinceauToucheBlancheRelache;
	private Paint pinceauToucheNoireJouableAppuye;
	private Paint pinceauToucheBlancheJouableAppuye;
	private Paint pinceauToucheTonique;
	private Paint pinceauToucheToniqueAppuye;

	/*
	 * Gestion des sons
	 */

	SoundPool soundPool;
	int[] tabSonTouchesBlanches = new int[7 * nbreOctave];
	int[] tabSonTouchesNoires = new int[7 * nbreOctave];
	float volume;
	float volumeProportion;
	float vol;
	public boolean sustain = true;

	/*
	 * Deux constructeurs
	 */
	public Piano(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		contexte = context;
		DisplayMetrics metrics = contexte.getResources().getDisplayMetrics();
		widthScreen = metrics.widthPixels;
		heightScreen = metrics.heightPixels;
		// init();

	}

	public Piano(Context context, AttributeSet attrs) {
		super(context, attrs);
		contexte = context;
		DisplayMetrics metrics = contexte.getResources().getDisplayMetrics();
		widthScreen = metrics.widthPixels;
		heightScreen = metrics.heightPixels;

		// init();

	}

	private void init() {
		/*
		 * Mise en place graphique
		 */

		// Calcul des diffÃ©rentes longueurs
		largeur = (int) (widthScreen * proportionPianoHorizontale);
		hauteur = (int) (heightScreen * proportionPianoVerticale);
		largeurToucheBlanche = (int) largeur / 7;
		largeurToucheNoire = (int) (largeurToucheBlanche * proportionToucheNoireLargeur);
		hauteurToucheNoire = (int) (hauteur * proportionToucheNoireHauteur);
		hauteurToucheBlanche = (int) hauteur;
		largeurTotale = largeurToucheBlanche * 7 * nbreOctave;

		// Mise en place des couleurs.

		int vertToucheBlancheJouable = Color.rgb(10, 190, 10);
		int vertToucheNoireJouable = Color.rgb(20, 90, 30);
		int bleuToucheTonique = Color.rgb(20, 150, 130);

		// CrÃ©ation des pinceaux

		pinceauToucheNoireAppuye = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheNoireAppuye.setShader(new LinearGradient(0, 0, 0,
				hauteurToucheNoire, new int[] { 0xFF000000, 0xff888888 }, null,
				TileMode.MIRROR));

		pinceauToucheNoireRelache = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheNoireRelache.setColor(Color.BLACK);

		pinceauToucheBlancheAppuye = new Paint();
		pinceauToucheBlancheAppuye.setShader(new LinearGradient(0, 0, 0,
				hauteur, new int[] { 0xFFFFFFFF, 0xFFAAAAAA }, null,
				TileMode.MIRROR));

		pinceauToucheNoireJouable = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheNoireJouable.setColor(vertToucheNoireJouable);

		pinceauToucheBlancheJouable = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheBlancheJouable.setColor(vertToucheBlancheJouable);

		pinceauToucheBlancheRelache = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheBlancheRelache.setColor(Color.WHITE);

		pinceauToucheBlancheJouableAppuye = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheBlancheJouableAppuye.setShader(new LinearGradient(0, 0, 0,
				hauteurToucheNoire, new int[] { vertToucheBlancheJouable,
						0xFFAAAAAA }, null, TileMode.MIRROR));

		pinceauToucheNoireJouableAppuye = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheNoireJouableAppuye.setShader(new LinearGradient(0, 0, 0,
				hauteurToucheNoire, new int[] { vertToucheNoireJouable,
						0xff888888 }, null, TileMode.MIRROR));

		pinceauToucheTonique = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheTonique.setColor(bleuToucheTonique);

		pinceauToucheToniqueAppuye = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheToniqueAppuye.setShader(new LinearGradient(0, 0, 0,
				hauteurToucheNoire,
				new int[] { bleuToucheTonique, 0xFFAAAAAA }, null,
				TileMode.MIRROR));

		init = true;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		this.setMeasuredDimension((int) (widthScreen
				* proportionPianoHorizontale * nbreOctave),
				(int) (heightScreen * proportionPianoVerticale));
		HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(
				(int) (widthScreen * proportionPianoHorizontale * nbreOctave),
				(int) (heightScreen * proportionPianoVerticale));
		this.setLayoutParams(params);
		// super.onMeasure((int) (widthMeasureSpec), heightMeasureSpec);
	}

	public void onDraw(Canvas canvas) {

		if (!init)
			init();

		// Coloration des touches blanches
		for (int i = 1; i <= 7 * nbreOctave; i++) {

			if (tabEtatTouchesBlanches[i - 1] == true) {

				if ((i) % 7 == tonique + 1) {
					canvas.drawRect(new Rect((i - 1) * largeurToucheBlanche, 0,
							i * largeurToucheBlanche, hauteur),
							pinceauToucheToniqueAppuye);

				} else {
					if (this.gamme((i - 1) % 7)) {
						canvas.drawRect(new Rect(
								(i - 1) * largeurToucheBlanche, 0, i
										* largeurToucheBlanche, hauteur),
								pinceauToucheBlancheJouableAppuye);
					} else {
						canvas.drawRect(new Rect(
								(i - 1) * largeurToucheBlanche, 0, i
										* largeurToucheBlanche, hauteur),
								pinceauToucheBlancheAppuye);
					}

				}

			} else if (i % 7 == tonique + 1) {
				canvas.drawRect(new Rect((i - 1) * largeurToucheBlanche, 0, i
						* largeurToucheBlanche, hauteur), pinceauToucheTonique);

			}

			else if (this.gamme((i - 1) % 7)) {
				canvas.drawRect(new Rect((i - 1) * largeurToucheBlanche, 0, i
						* largeurToucheBlanche, hauteur),
						pinceauToucheBlancheJouable);
			}

			else {
				canvas.drawRect(new Rect((i - 1) * largeurToucheBlanche, 0, i
						* largeurToucheBlanche, hauteur),
						pinceauToucheBlancheRelache);
			}

			canvas.drawLine(i * largeurToucheBlanche, 0, i
					* largeurToucheBlanche, hauteur, new Paint());
		}

		// CrÃ©ation des diÃ¨ses
		for (int i = 1; i <= 7 * nbreOctave; i++) {
			if ((i % 7) != 3 && (i % 7) != 0) { // Pas de touche noire entre le
												// Mi et le Fa, ni entre le Do
												// et le si

				// Cas appuyÃ©
				if (tabEtatTouchesNoires[i - 1]) {

					if ((i % 7) + 6 == tonique) {
						canvas.drawRoundRect(
								new RectF(
										i
												* largeurToucheBlanche
												- (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										-10,
										i
												* largeurToucheBlanche
												+ (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										(int) (hauteur * proportionToucheNoireHauteur)),
								5, 10, pinceauToucheToniqueAppuye);
					} else if (this.gamme(i % 7 + 7)) {
						canvas.drawRoundRect(
								new RectF(
										i
												* largeurToucheBlanche
												- (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										-10,
										i
												* largeurToucheBlanche
												+ (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										(int) (hauteur * proportionToucheNoireHauteur)),
								5, 10, pinceauToucheNoireJouableAppuye);
					} else {
						canvas.drawRoundRect(
								new RectF(
										i
												* largeurToucheBlanche
												- (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										-10,
										i
												* largeurToucheBlanche
												+ (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										(int) (hauteur * proportionToucheNoireHauteur)),
								5, 10, pinceauToucheNoireAppuye);
					}
				}

				// Cas pas appuye

				else {
					if ((i % 7) + 6 == tonique) {
						canvas.drawRoundRect(
								new RectF(
										i
												* largeurToucheBlanche
												- (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										-10,
										i
												* largeurToucheBlanche
												+ (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										(int) (hauteur * proportionToucheNoireHauteur)),
								5, 10, pinceauToucheTonique);
					} else if (this.gamme(i % 7 + 7)) {
						canvas.drawRoundRect(
								new RectF(
										i
												* largeurToucheBlanche
												- (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										-10,
										i
												* largeurToucheBlanche
												+ (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										(int) (hauteur * proportionToucheNoireHauteur)),
								5, 10, pinceauToucheNoireJouable);
					} else {
						canvas.drawRoundRect(
								new RectF(
										i
												* largeurToucheBlanche
												- (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										-10,
										i
												* largeurToucheBlanche
												+ (int) (largeurToucheBlanche * proportionToucheNoireLargeur),
										(int) (hauteur * proportionToucheNoireHauteur)),
								5, 10, pinceauToucheNoireRelache);
					}

				}
			}
		}

		// On trace la dï¿½limitation entre les touches une par une. D'abord si
		// il
		// y a une touche noire.
		for (int j = 1; j < 7 * nbreOctave; j++) {
			if ((j % 7) != 3 && (j % 7) != 0) {
				// On trace la dï¿½limitation entre les touches une par une.
				// D'abord si il y a une touche noire.
				canvas.drawLine(j * largeurToucheBlanche, hauteurToucheNoire, j
						* largeurToucheBlanche, hauteur, new Paint());
			}

			else {
				// S'il n'y a pas de touche noire entre les deux touches
				canvas.drawLine(j * largeurToucheBlanche, 0, j
						* largeurToucheBlanche, hauteur, new Paint());
				canvas.drawLine(j * largeurToucheBlanche, 0, j
						* largeurToucheBlanche, hauteur, new Paint());
			}

		}

		// Dessin des lignes en haut et en bas.
		canvas.drawLine(0, hauteurToucheBlanche - 1, 7 * nbreOctave
				* largeurToucheBlanche, hauteurToucheBlanche - 1, new Paint());
		canvas.drawLine(0, 0, 7 * nbreOctave * largeurToucheBlanche, 0,
				new Paint());
	}

	public boolean onTouchEvent(MotionEvent event) {
		int ev = MotionEventCompat.getActionMasked(event);
		// int ev = event.getActionMasked();

		int toucheNoireAppuye = -1;

		if (ev != MotionEvent.ACTION_MOVE) {

			// Index du pointeur
			int pointerIndex = MotionEventCompat.getActionIndex(event);
			int pointerId = event.getPointerId(pointerIndex);

			// CoordonnÃ©es de l'Ã©vÃ¨nement
			x = (int) MotionEventCompat.getX(event, pointerIndex);
			y = (int) MotionEventCompat.getY(event, pointerIndex);
			// Numero de la touche sur le clavier
			int indexTouche = (int) Math.floor((double) x / (double) largeur
					* 7.0); // Index de la touche blanche correspondant Ã  la
							// position (si s'en est une ...)

			// Seulement si on est bien sur le piano
			if (y < hauteurToucheBlanche) {

				if (ev == MotionEvent.ACTION_UP
						|| ev == MotionEvent.ACTION_POINTER_UP) {
					int toucheCorrespondante = positionPointeurs.get(pointerId);
					positionPointeurs.remove(pointerId);

					if (!positionPointeurs.containsValue(toucheCorrespondante)) { // Si
																					// aucun
																					// (autre)
																					// pointeur
																					// n'est
																					// dessus
						if (toucheCorrespondante >= 10 * nbreOctave) { // C'est
																		// une
																		// touche
																		// noire
																		// sur
																		// laquelle
																		// ce
																		// pointeur
																		// etait
																		// appuyÃ©
							tabEtatTouchesNoires[toucheCorrespondante - 10
									* nbreOctave] = false;
							int ancienSon = soundids.get(toucheCorrespondante);
							this.stopNote(ancienSon);
						} else { // Sinon, c'est qu'elle est blanche
							tabEtatTouchesBlanches[toucheCorrespondante] = false;
							int ancienSon = soundids.get(toucheCorrespondante);
							this.stopNote(ancienSon);

						}
					}
				}

				else if (ev == MotionEvent.ACTION_DOWN
						|| ev == MotionEvent.ACTION_POINTER_DOWN) {

					toucheNoireAppuye = isNoire(x, y);
					// Si l'on est sur une touche blanche
					if (toucheNoireAppuye == -1) {
						if (soundids.get(indexTouche) != null) {
							soundPool.stop(soundids.get(indexTouche));
						}
						tabEtatTouchesBlanches[indexTouche] = true;
						positionPointeurs.put(pointerId, indexTouche);
						int a = soundPool.play(
								tabSonTouchesBlanches[indexTouche], volume,
								volume, 1, 0, 1f);
						soundids.put(indexTouche, a);
					}
					// Sinon l'on est sur une noire
					else {
						tabEtatTouchesNoires[toucheNoireAppuye] = true;
						positionPointeurs.put(pointerId, toucheNoireAppuye + 10
								* nbreOctave);
						if (soundids.get(toucheNoireAppuye) != null) {
							soundPool.stop(soundids.get(toucheNoireAppuye));
						}
						int a = soundPool.play(
								tabSonTouchesNoires[toucheNoireAppuye], volume,
								volume, 1, 0, 1f);
						soundids.put(toucheNoireAppuye + 10 * nbreOctave, a);

					}
				}

				// Mise Ã  jour Ã©cran

			}
		}

		else if (ev == MotionEvent.ACTION_MOVE) {

			for (int index = 0; index < event.getPointerCount(); index++) {
				int pointerId = event.getPointerId(index);

				// CoordonnÃ©es de l'Ã©vÃ¨nement
				x = (int) MotionEventCompat.getX(event, index);
				y = (int) MotionEventCompat.getY(event, index);

				// Seulement si on est bien sur le piano

				if (y < hauteurToucheBlanche) {

					int indexTouche;
					if (x > 0) {
						indexTouche = (int) Math.floor((double) x
								/ (double) largeur * 7);
					} else {
						indexTouche = 0;
					}

					toucheNoireAppuye = isNoire(x, y);

					int nouvelleToucheCorrespondante = (toucheNoireAppuye == -1) ? indexTouche
							: (toucheNoireAppuye + 10 * nbreOctave);
					int toucheCorrespondante;

					// Si l'on ne vient pas du piano, toucheCorrespondante=-1

					if (positionPointeurs.get(pointerId) != null) {
						toucheCorrespondante = positionPointeurs.get(pointerId);

					} else {

						toucheCorrespondante = -1;
					}

					// Si on a pas changÃ© de touche, on quitte le if
					// ACTION_MOVE,
					if (toucheCorrespondante != nouvelleToucheCorrespondante) {
						// Sinon, on calcule la nouvelle position et on enlÃ¨ve
						// l'ancienne touche si personne n'est dessus

						// Si l'on est sur une touche blanche
						if (toucheNoireAppuye == -1) {

							tabEtatTouchesBlanches[indexTouche] = true;
							positionPointeurs.put(pointerId, indexTouche);

							int b = soundPool.play(
									tabSonTouchesBlanches[indexTouche], volume,
									volume, 1, 0, 1f);

							soundids.put(indexTouche, b);

							if (!positionPointeurs
									.containsValue(toucheCorrespondante)) { // Si
																			// aucun
																			// (autre)
																			// pointeur
																			// n'est
																			// dessus
								if (toucheCorrespondante >= 10 * nbreOctave) { // C'est
																				// une
																				// touche
																				// noire
																				// sur
																				// lequelle
																				// ce
																				// pointeur
																				// etait
																				// appuyÃ©

									this.stopNote(toucheCorrespondante);
									tabEtatTouchesNoires[toucheCorrespondante
											- 10 * nbreOctave] = false;
								} else { // Sinon, c'est qu'elle ï¿½tait blanche
									if (toucheCorrespondante != -1) {
										tabEtatTouchesBlanches[toucheCorrespondante] = false;
										this.stopNote(toucheCorrespondante);

									}

								}
							}

						}
						// Sinon l'on est sur une noire
						else {
							tabEtatTouchesNoires[toucheNoireAppuye] = true;
							positionPointeurs.put(pointerId,
									nouvelleToucheCorrespondante);

							int b = soundPool.play(
									tabSonTouchesNoires[toucheNoireAppuye],
									volume, volume, 1, 0, 1f);

							soundids.put(toucheNoireAppuye + 10 * nbreOctave, b);
							if (toucheCorrespondante != -1) {
								if (!positionPointeurs
										.containsValue(toucheCorrespondante)) { // Si
																				// aucun
																				// (autre)
																				// pointeur
																				// n'est
																				// dessus
									if (toucheCorrespondante >= 10 * nbreOctave) { // C'est
																					// une
																					// touche
																					// noire
																					// sur
																					// lequelle
																					// ce
																					// pointeur
																					// etait
																					// appuyÃ©
										tabEtatTouchesNoires[toucheCorrespondante
												- 10 * nbreOctave] = false;
										this.stopNote(toucheCorrespondante);
									} else { // Sinon, c'est qu'elle est blanche

										tabEtatTouchesBlanches[toucheCorrespondante] = false;
										this.stopNote(toucheCorrespondante);

									}
								}

							}
						}
					}

				}

				else {

				}

			}
		}

		invalidate();

		return true;
	}

	/**
	 * Fonction qui renvoie -1 si ce n'est pas une noire, et renvoie le numéro
	 * de cette touche noire sinon
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private int isNoire(int x, int y) {
		// Si l'on est forcÃ©ment sur une touche blanche
		if (y > hauteurToucheNoire) {
			return -1;
		}
		// Sinon on peut Ãªtre ou sur une noire, ou sur une blanche
		else {

			for (int k = 1; k < 7 * nbreOctave; k++) {
				if (x < (k * largeurToucheBlanche + largeurToucheNoire)
						&& x > (k * largeurToucheBlanche - largeurToucheNoire)
						&& (k % 7) != 3 && (k % 7) != 0) {
					return (k - 1);
				}
			}
			return -1;
		}
	}

	/**
	 * Méthode qui rÃ©cupÃ¨re les infos pour le son et les stocke dans deux
	 * tableaux ayant la mÃªme structure que le tableau des Ã©tats des touches
	 */
	void recupererSon(SoundPool s, int[] sons, float v) {
		soundPool = s;
		volume = v;

		for (int i = 0; i < nbreOctave; i++) {

			tabSonTouchesBlanches[7 * i] = sons[12 * i];
			tabSonTouchesNoires[7 * i] = sons[12 * i + 1];
			tabSonTouchesBlanches[7 * i + 1] = sons[12 * i + 2];
			tabSonTouchesNoires[7 * i + 1] = sons[12 * i + 3];
			tabSonTouchesBlanches[7 * i + 2] = sons[12 * i + 4];
			tabSonTouchesBlanches[7 * i + 3] = sons[12 * i + 5];
			tabSonTouchesNoires[7 * i + 3] = sons[12 * i + 6];
			tabSonTouchesBlanches[7 * i + 4] = sons[12 * i + 7];
			tabSonTouchesNoires[7 * i + 4] = sons[12 * i + 8];
			tabSonTouchesBlanches[7 * i + 5] = sons[12 * i + 9];
			tabSonTouchesNoires[7 * i + 5] = sons[12 * i + 10];
			tabSonTouchesBlanches[7 * i + 6] = sons[12 * i + 11];
		}

	}

	private boolean gamme(int i) {

		return gamme[i];
	}

	public void setGamme(boolean[] gmou) {
		gamme = gmou;
	}

	public void setTonique(int fondamentale) {
		tonique = fondamentale;
	}

	public void setScreenWidth(int width) {
		screenWidth = width;
	}

	public void setSustain(boolean a) {
		sustain = a;
	}

	public boolean getSustain() {
		return sustain;
	}

	public void stopNote(final int Note) {
		if (!sustain) {

			Handler lHandler = new Handler();

			lHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.33 * volumeProportion);
				}
			}, 20);

			Handler mHandler = new Handler();

			mHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.17 * volumeProportion);
				}
			}, 200);

			Handler nHandler = new Handler();

			nHandler.postDelayed(new Runnable() {
				public void run() {

					doStuff(Note, (float) 0.07 * volumeProportion);
				}
			}, 350);

			Handler pHandler = new Handler();
			pHandler.postDelayed(new Runnable() {
				public void run() {
					doStuffbis(Note);
				}
			}, 600);

		}

	}

	private void doStuff(int Note, float vr) {
		soundPool.setVolume(Note, vr, vr);

	}

	private void doStuffbis(int Note) {
		soundPool.stop(Note);
	}

	public void setVolume(float v) {
		volume = v;
		volumeProportion = (float) (v / 0.55);

	}

	public int getHauteur() {
		return hauteurToucheBlanche;
	}

	public void setProportionPianoVerticale(double a) {
		this.proportionPianoVerticale = a;
	}

	public double getProportionPianoVerticale() {
		return this.proportionPianoVerticale;
	}

	public double getProportionPianoHorizontale() {
		return proportionPianoHorizontale;
	}

	public int getLargeurToucheBlanche() {
		return this.largeurToucheBlanche;
	}

	public void setProportionPianoHorizontale(double prop) {

		this.proportionPianoHorizontale = prop;

		// On refait l'init() et le OnMeasure() Parce que tout change!

		largeur = (int) (widthScreen * proportionPianoHorizontale);
		hauteur = (int) (heightScreen * proportionPianoVerticale);
		largeurToucheBlanche = (int) largeur / 7;
		largeurToucheNoire = (int) (largeurToucheBlanche * proportionToucheNoireLargeur);
		hauteurToucheNoire = (int) (hauteur * proportionToucheNoireHauteur);
		hauteurToucheBlanche = (int) hauteur;
		largeurTotale = largeurToucheBlanche * 7 * nbreOctave;
		this.setMeasuredDimension((int) (widthScreen
				* proportionPianoHorizontale * nbreOctave),
				(int) (heightScreen * proportionPianoVerticale));
		this.setLayoutParams(new HorizontalScrollView.LayoutParams(
				(int) (widthScreen * proportionPianoHorizontale * nbreOctave),
				(int) (heightScreen * proportionPianoVerticale)));
	}

	public int positionTouche(int i) {
		if (i < 7) {
			if (i < 5) {
				int a = (7 + i) * largeurToucheBlanche;
				return a;
			}

			else {
				int a = i * largeurToucheBlanche;
				return a;
			}
		} else {
			int a = largeurToucheBlanche * (i - 7) + largeurToucheBlanche
					- largeurToucheNoire / 2;
			return a;
		}

	}
}
