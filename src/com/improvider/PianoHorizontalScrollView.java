package com.improvider;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.OverScroller;

public class PianoHorizontalScrollView extends HorizontalScrollView {

	public double proportionHauteur = 0.86;
	private OverScroller myScroller;
	private ImageScroller imageScroller;

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

		final int width = getWidth() - getPaddingRight() - getPaddingLeft();
		final int right = getChildAt(0).getWidth();
		final int maxX = Math.max(0, right - width);
		final int scrollX = getScrollX();
		dx = Math.max(0, Math.min(scrollX + dx, maxX)) - scrollX;

		myScroller.startScroll(scrollX, getScrollY(), dx, 0, 2800);
		imageScroller.invalidate();
	}

	public void customSmoothScrollTo(int x, int y) {
		customSmoothScrollBy(x - getScrollX(), y - getScrollY());
	}

	public void setImageScroller(ImageScroller imageScroller) {
		this.imageScroller = imageScroller;
	}

}
