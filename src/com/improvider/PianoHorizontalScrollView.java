package com.improvider;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.OverScroller;

/**
 * Une custom HorizontalScrollView pour changer la gestion des évènements, et
 * rajouter un CustomSmoothScroll qui va servir lors du premier clic sur la tab
 * "Piano". Le passage sur l'OverScroller est un peu compliquée, c/p direct
 * d'internet.
 * 
 * @author Lionel
 * 
 */

public class PianoHorizontalScrollView extends HorizontalScrollView {

	public double proportionHauteur = 0.86;
	private OverScroller myScroller;
	private final int TIME_SMOOTH_SCROLL=5000;

	public PianoHorizontalScrollView(Context context) {

		// Récupération de l'overScroller pour le customSmoothScroll
		super(context);
		try {
			Class<?> parent = this.getClass();
			do {
				parent = parent.getSuperclass();
			} while (!parent.getName().equals(
					"android.widget.HorizontalScrollView"));

			java.lang.reflect.Field field = parent
					.getDeclaredField("mScroller");
			field.setAccessible(true);
			myScroller = (OverScroller) field.get(this);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	public PianoHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Récupère le OverScroll, nécessaire pour le customSmoothScroll
		try {
			Class<?> parent = this.getClass();
			do {
				parent = parent.getSuperclass();
			} while (!parent.getName().equals(
					"android.widget.HorizontalScrollView"));

			java.lang.reflect.Field field = parent
					.getDeclaredField("mScroller");
			field.setAccessible(true);
			myScroller = (OverScroller) field.get(this);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	public PianoHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		try {
			Class<?> parent = this.getClass();
			do {
				parent = parent.getSuperclass();
			} while (!parent.getName().equals(
					"android.widget.HorizontalScrollView"));

			java.lang.reflect.Field field = parent
					.getDeclaredField("mScroller");
			field.setAccessible(true);
			myScroller = (OverScroller) field.get(this);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// Et ben oui, on ne fait plus rien, tous les scrollages sont appelés
		// par imageScroller
		return false;
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		// Pareil
		return false;
	}

	public void customSmoothScrollBy(int dx, int dy) {

		if (getChildCount() == 0) {
			return;
		}
		/*Texte servant normalement à adapter le customSmoothScrollBy à toute situation mais certaines variables mettent un
		 * peu de temps à s'instancier, donc ça marchait pas au premier affichage, seul moment où on s'en sert...
		 * 
		Log.d("dx Avant calcul", String.valueOf(dx));
		final int width = getWidth() - getPaddingRight() - getPaddingLeft();
		Log.d("width", String.valueOf(width));
		final int right = getChildAt(0).getWidth();
		Log.d("rigth", String.valueOf(right));
		final int maxX = Math.max(0, right - width);
		Log.d("maxX", String.valueOf(maxX));
		final int scrollX = getScrollX();
		Log.d("scrollX", String.valueOf(scrollX));
		dx = Math.max(0, Math.min(scrollX + dx, maxX)) - scrollX;
		*/		
		myScroller.startScroll(0, 0, dx, 0, TIME_SMOOTH_SCROLL);
		
		
	}

	public void customSmoothScrollTo(int x, int y) {
		customSmoothScrollBy(x - getScrollX(), y - getScrollY());
	}


}
