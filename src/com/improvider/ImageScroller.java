package com.improvider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Cette classe sert de controleur graphique pour le pianoHorizontalScrollView
 * du main. Tout est pareil que Piano sauf les proportions et surtout le
 * onTouchEvent, où se trouve le zoom-in, zoom-out, et le scrolling. Egalement
 * des bouts de codes ont été supprimés au niveau des attributs, de l'init et du
 * onDraw pour ne surcharger le code.
 */

public class ImageScroller extends View {

	Piano piano;
	PianoHorizontalScrollView scroller;

	/*
	 * Constantes de la classe
	 */

	private final static double proportionToucheNoireHauteur = 0.75;
	private final static double proportionToucheNoireLargeur = 0.32;
	private final static int nbreOctave = 3;

	// C'est ici qu'on définit la taille de l'image scroller, c'est la seule
	// chose graphique qui change par rapport au vrai Piano.
	private double proportionPianoVerticale = 0.10;
	private double proportionPianoHorizontale = 0.22;

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

	// Variables de scroll
	private int x1 = 0;
	private int dx1 = 0;

	// Variable de zoom
	private float x3;
	private float x4;
	private float dx3 = 0;
	private float dx4 = 0;
	boolean justZoomed = false;
	// proportionInitiale sert juste à donner une idée pour ensuite construire
	// la constante multiplicative pour le zoom
	private double proportionInitiale = 0.875;

	// Context
	Context contexte;

	// Pour colorer
	private boolean[] gamme;
	private int tonique = 0;

	// On vérifie qu'on a bien initié avant d'essayer de dessiner.
	boolean init = false;

	/*
	 * Les pinceaux
	 */
	private Paint pinceauToucheNoireRelache;
	private Paint pinceauToucheNoireJouable;
	private Paint pinceauToucheBlancheJouable;
	private Paint pinceauToucheBlancheRelache;
	private Paint pinceauToucheTonique;

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
		int vertToucheNoireJouable = Color.rgb(20, 70, 30);
		int bleuToucheTonique = Color.rgb(20, 150, 130);

		// CrÃ©ation des pinceaux

		pinceauToucheNoireRelache = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheNoireRelache.setColor(Color.BLACK);

		pinceauToucheNoireJouable = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheNoireJouable.setColor(vertToucheNoireJouable);

		pinceauToucheBlancheJouable = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheBlancheJouable.setColor(vertToucheBlancheJouable);

		pinceauToucheBlancheRelache = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheBlancheRelache.setColor(Color.WHITE);

		pinceauToucheTonique = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauToucheTonique.setColor(bleuToucheTonique);

		pinceauRectangle = new Paint(Paint.ANTI_ALIAS_FLAG);
		pinceauRectangle.setColor(bleuToucheTonique);
		pinceauRectangle.setAlpha(200);

		init = true;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

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

			if (i % 7 == tonique + 1) {
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

		// Dessin des lignes pour encadrer l'imageScroller
		canvas.drawLine(0, hauteurToucheBlanche-1, 7 * nbreOctave
				* largeurToucheBlanche, hauteurToucheBlanche-1, new Paint());
		canvas.drawLine(0, 0, 7 * nbreOctave * largeurToucheBlanche, 0,
				new Paint());

		canvas.drawLine(1, 1, 1, hauteurToucheBlanche, new Paint());

		canvas.drawLine(7 * largeurToucheBlanche * nbreOctave - 1, 0, 7
				* largeurToucheBlanche * nbreOctave - 1, hauteurToucheBlanche,
				new Paint());

		// Dessin du rectangle représentant l'espace du piano qu'on voit
		canvas.drawRoundRect(

				new RectF(
						(float) (largeurToucheBlanche * scroller.getScrollX()
								* this.getNbreTouchePiano() / screenWidth),
						0,
						(float) (largeurToucheBlanche * scroller.getScrollX()
								* this.getNbreTouchePiano() / screenWidth + largeurToucheBlanche
								* this.getNbreTouchePiano()),
						hauteurToucheBlanche), 5, 10, pinceauRectangle);

		// Semble être une mauvaise pratique, mais apparemment acceptable pour
		// les animations (scroll auto au premier clic sur la tab piano)
		invalidate();

	}

	/**
	 * Seule méthode intéressante de cette classe, permet le zoom-in et zoom-out
	 * et le déplacement (scroll), en utilisant les évènement tactiles et en
	 * appelant le PianoHorizontalScrollView (scroll et zoom) ainsi que
	 * l'attribut proportionPianoHorizontale (zoom seulement)
	 */
	public boolean onTouchEvent(MotionEvent event) {
		int ev = MotionEventCompat.getActionMasked(event);
		int pointerCount = event.getPointerCount();

		// Ici on fait tout à l'ancienne! On peut avoir besoin d'un petit dessin
		// pour bien comprendre...

		// Pour zoom-in, zoom-out

		if (pointerCount == 2) {

			float x3b = event.getX(0);
			float x4b = event.getX(1);

			dx3 = x3b - x3;
			dx4 = x4b - x4;

			if (ev == MotionEvent.ACTION_MOVE && x3 != 0) {
				double prop2;
				double prop1 = this.piano.getProportionPianoHorizontale();
				float k = (float) (0.2 * this.screenWidth / (this.proportionInitiale));
				boolean hasBeenZoomed = false;
				int positionScrollInitiale = this.scroller.getScrollX();

				// Deux cas car on ne sait pas forcément quel pointeur est celui
				// de x plus grand.
				if (x4 > x3) {

					prop2 = prop1 - (dx4 - dx3) / k;

					double octaves = (double) nbreOctave;
					double maille = 1 / octaves;

					if (prop2 > maille && prop2 < 0.5 * nbreOctave) {
						this.piano.setProportionPianoHorizontale(prop2);
						hasBeenZoomed = true;
					}
				}

				else {
					prop2 = prop1 - (dx3 - dx4) / k;

					double octaves = (double) nbreOctave;
					double maille = 1 / octaves;

					if (prop2 > maille && prop2 < 0.5 * nbreOctave) {
						this.piano.setProportionPianoHorizontale(prop2);
						hasBeenZoomed = true;
					}
				}

				// Scroll de replaçage pendant le redimensionnement, pour garder
				// à peu près le même centre
				if (hasBeenZoomed) {
					int newPositionToScroll = (int) ((prop2 / prop1
							* positionScrollInitiale + this.screenWidth
							* ((prop2 / prop1) - 1)));

					int correction = (int) ((4 * (prop1 - prop2) * this.screenWidth) / 5);

					if (newPositionToScroll > 0
							&& newPositionToScroll < this.piano
									.getLargeurToucheBlanche() * 7 * nbreOctave) { //
						this.scroller.scrollTo(
								newPositionToScroll + correction, 0);
// GitHub c'est pas mal, c'est cool.
					}
				}
				justZoomed = true;

				// Si au moins un des deux pointeurs et relevé, on
				// réinitialise les variables de scroll.
				if (ev == MotionEvent.ACTION_POINTER_UP) {

					x3 = 0;
					x4 = 0;
					dx3 = 0;
					dx4 = 0;

				}

			}

			x3 = x3b;
			x4 = x4b;

		} else {

			// Pour le simple déplaçage/scroll
			if (pointerCount == 1) {
				// Le if pour éviter de replacer quand on enlève le 2eme doigt
				// après avoir zoom.
				if (justZoomed == false) {
					int pointerIndex = MotionEventCompat.getActionIndex(event);
					int pointerId = event.getPointerId(pointerIndex);
					int y = (int) MotionEventCompat.getY(event, pointerIndex);
					int x = (int) MotionEventCompat.getX(event, pointerIndex);

					// Offset pour prendre comme référence le centre de la
					// fenêtre
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
		invalidate();
		return true;
	}

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
