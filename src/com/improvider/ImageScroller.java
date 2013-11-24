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

import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class ImageScroller extends View {

	Piano piano;
	PianoHorizontalScrollView scroller;

	/*
	 * Constantes de la classe
	 */

	private final static double proportionToucheNoireHauteur = 0.75;
	private final static double proportionToucheNoireLargeur = 0.32;
	private final static int nbreOctave = 3;
	private double proportionPianoVerticale = 0.11;

	private double proportionPianoHorizontale = 0.27;

	/*
	 * Id du pointeur, numero de la touche (en commencant à 0) (+ 10 si noire)
	 */
	Hashtable<Integer, Integer> positionPointeurs = new Hashtable<Integer, Integer>();
	// Map associant num�ro de soundclouds et touches (pour arr�ter le son).
	// Num�ro de soundCloud= entier croissant dans l'ordre de lancement.(1
	// pour
	// le premier soundcloud, 2 pour le suivant...)
	Hashtable<Integer, Integer> soundids = new Hashtable<Integer, Integer>();
	int largeur, hauteur, largeurTotale;
	int largeurToucheBlanche;
	int largeurToucheNoire;
	int hauteurToucheNoire;
	int hauteurToucheBlanche;
	int screenWidth;
	int heightScreen;
	int widthScreen;

	private int x1 = 0;
	private int dx1 = 0;

	private float x3;
	private float x4;
	private float dx3 = 0;
	private float dx4 = 0;

	boolean justZoomed = false;
	private double proportionInitiale = 0.875;

	Context contexte;

	private boolean[] gamme;
	public boolean sustain = true;
	private int tonique = 0;

	/*
	 * Attributs divers de la classe
	 */
	boolean[] tabEtatTouchesBlanches = new boolean[7 * nbreOctave];
	boolean[] tabEtatTouchesNoires = new boolean[7 * nbreOctave];
	int[] tabSonTouchesBlanches = new int[7 * nbreOctave];
	int[] tabSonTouchesNoires = new int[7 * nbreOctave];
	int x, y;
	boolean init = false;

	int ancienx, ancieny;

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

	private Paint pinceauRectangle;

	/*
	 * Gestion des sons
	 */

	/*
	 * Deux constructeurs
	 */
	public ImageScroller(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		contexte = context;
		DisplayMetrics metrics = contexte.getResources().getDisplayMetrics();
		widthScreen = metrics.widthPixels;
		heightScreen = metrics.heightPixels;
		// init();

	}

	public ImageScroller(Context context, AttributeSet attrs) {
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

		// Calcul des différentes longueurs
		largeur = (int) (widthScreen * proportionPianoHorizontale);
		hauteur = (int) (heightScreen * proportionPianoVerticale);
		largeurToucheBlanche = (int) largeur / 7;
		largeurToucheNoire = (int) (largeurToucheBlanche * proportionToucheNoireLargeur);
		hauteurToucheNoire = (int) (hauteur * proportionToucheNoireHauteur);
		hauteurToucheBlanche = (int) hauteur;
		largeurTotale = largeurToucheBlanche * 7 * nbreOctave;

		// Mise en place des couleurs.

		int vertToucheBlancheJouable = Color.rgb(10, 190, 10);
		int vertToucheNoireJouable = Color.rgb(20, 70, 30);
		int bleuToucheTonique = Color.rgb(20, 150, 130);

		// Création des pinceaux

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

		pinceauRectangle = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauRectangle.setColor(bleuToucheTonique);
		pinceauRectangle.setAlpha(200);

		init = true;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		this.setMeasuredDimension((int) (widthScreen
				* proportionPianoHorizontale * nbreOctave),
				(int) (heightScreen * proportionPianoVerticale));
		this.setLayoutParams(new HorizontalScrollView.LayoutParams(
				(int) (widthScreen * proportionPianoHorizontale * nbreOctave),
				(int) (heightScreen * proportionPianoVerticale)));
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

		// Création des dièses
		for (int i = 1; i <= 7 * nbreOctave; i++) {
			if ((i % 7) != 3 && (i % 7) != 0) { // Pas de touche noire entre le
												// Mi et le Fa, ni entre le Do
												// et le si

				// Cas appuyé
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

		// On trace la d�limitation entre les touches une par une. D'abord si
		// il
		// y a une touche noire.
		for (int j = 1; j < 7 * nbreOctave; j++) {
			if ((j % 7) != 3 && (j % 7) != 0) {
				// On trace la d�limitation entre les touches une par une.
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
		canvas.drawLine(0, hauteurToucheBlanche, 7 * nbreOctave
				* largeurToucheBlanche, hauteurToucheBlanche, new Paint());
		canvas.drawLine(0, 0, 7 * nbreOctave * largeurToucheBlanche, 0,
				new Paint());

		canvas.drawLine(0, 1, 0, hauteurToucheBlanche, new Paint());

		canvas.drawLine(7 * largeurToucheBlanche * nbreOctave - 1, 0, 7
				* largeurToucheBlanche * nbreOctave - 1, hauteurToucheBlanche,
				new Paint());

		canvas.drawRoundRect(

				new RectF(
						(float) (largeurToucheBlanche * scroller.getScrollX()
								* this.getNbreTouchePiano() / screenWidth),
						0,
						(float) (largeurToucheBlanche * scroller.getScrollX()
								* this.getNbreTouchePiano() / screenWidth + largeurToucheBlanche
								* this.getNbreTouchePiano()),
						hauteurToucheBlanche), 5, 10, pinceauRectangle);

		invalidate();

	}

	public boolean onTouchEvent(MotionEvent event) {
		int ev = MotionEventCompat.getActionMasked(event);
		int pointerCount = event.getPointerCount();

		// Pour zoom-in, zoom-out

		if (pointerCount == 2) {

			float x3b = event.getX(0);
			float x4b = event.getX(1);

			dx3 = x3b - x3;
			dx4 = x4b - x4;

			if (ev == MotionEvent.ACTION_MOVE && x3 != 0) {
				double prop2;
				double prop1 = this.piano.getProportionPianoHorizontale();
				float k = (float) (0.2*this.screenWidth / (this.proportionInitiale));

				int positionScrollInitiale = this.scroller.getScrollX();

				if (x4 > x3) {

					prop2 = prop1 - (dx4 - dx3) / k;

					double octaves = (double) nbreOctave;
					double maille = 1 / octaves;

					if (prop2 > maille && prop2 < 2 * nbreOctave) {
						this.piano.setProportionPianoHorizontale(prop2);
					}
				}

				else {
					prop2 = prop1 - (dx3 - dx4) / k;

					double octaves = (double) nbreOctave;
					double maille = 1 / octaves;

					if (prop2 > maille && prop2 < 2 * nbreOctave) {
						this.piano.setProportionPianoHorizontale(prop2);
					}
				}

				// Scroll de repla�age pendant le redimensionnement

				int newPositionToScroll = (int) ((prop2 / prop1
						* positionScrollInitiale + this.screenWidth
						* ((prop2 / prop1) - 1)));

				int correction = (int) ((4 * (prop1 - prop2) * this.screenWidth) / 5);

				if (newPositionToScroll > 0
						&& newPositionToScroll < this.piano
								.getLargeurToucheBlanche() * 7 * nbreOctave) { //
					this.scroller.scrollTo(newPositionToScroll + correction, 0);
					justZoomed = true;

					if (ev == MotionEvent.ACTION_POINTER_UP) {

						x3 = 0;
						x4 = 0;
						dx3 = 0;
						dx4 = 0;

					}

				}
			}

			x3 = x3b;
			x4 = x4b;

		} else {

			if (pointerCount == 1) {
				if (justZoomed == false) {
					int pointerIndex = MotionEventCompat.getActionIndex(event);
					int pointerId = event.getPointerId(pointerIndex);
					int y = (int) MotionEventCompat.getY(event, pointerIndex);
					int x = (int) MotionEventCompat.getX(event, pointerIndex);
					int offsetMedium = (int) ((largeurToucheBlanche * this
							.getNbreTouchePiano()) / 2);
					int x2 = x - offsetMedium;
					dx1 = x2 - x1;
					x1 = x2;
					int toMove = (int) ((dx1)
							* this.piano.getProportionPianoHorizontale() / this.proportionPianoHorizontale);
					this.scroller.scrollBy(toMove, 0);

					if (ev == MotionEvent.ACTION_UP) {
						x1 = (int) (this.scroller.getScrollX()
								* this.proportionPianoHorizontale / this.piano
								.getProportionPianoHorizontale());
						dx1 = 0;
					}
				}
				x3 = 0;
				x4 = 0;
				dx3 = 0;
				dx4 = 0;

				justZoomed = false;
			}

		}

		return true;
	}

	// Fonction qui récupère les infos pour le son et les stocke dans deux
	// tableaux ayant la même structure que le tableau des états des touches

	private boolean gamme(int i) {
		// TODO Auto-generated method stub
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

	public int getHauteur() {
		return hauteurToucheBlanche;
	}

	public void setProportionPianoVerticale(double d) {
		this.proportionPianoVerticale = d;

	}

	public double getProportionPianoVerticale() {
		return this.proportionPianoVerticale;
	}

	public double getProportionPianoHorizontale() {
		return proportionPianoHorizontale;
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

	public int getLargeurToucheBlanche() {
		return this.largeurToucheBlanche;
	}

	public float getNbreTouchePiano() {
		double prop = this.piano.getProportionPianoHorizontale();
		float nbreTouche = (float) (7 / prop);
		return nbreTouche;

	}

	public void setPiano(Piano piano) {
		this.piano = piano;
	}

	public void setPianoHorizontalScrollView(PianoHorizontalScrollView scroller) {
		this.scroller = scroller;
	}

	public void setX1(int x) {
		int x2 = (int) (x * this.proportionPianoHorizontale / this.piano
				.getProportionPianoHorizontale());
		this.x1 = x2;
	}

	public void setProportionInitiale(double prop) {
		this.proportionInitiale = prop;
	}

}
