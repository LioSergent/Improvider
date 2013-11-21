package com.improvider;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.OverScroller;

public class PianoHorizontalScrollView extends HorizontalScrollView {

	public double proportionHauteur = 0.86;
	private OverScroller myScroller;
	private ImageScroller imageScroller;

	public PianoHorizontalScrollView(Context context) {

		super(context);
		try {
			Class parent = this.getClass();
			do {
				parent = parent.getSuperclass();
			} while (!parent.getName().equals(
					"android.widget.HorizontalScrollView"));

			Log.i("Scroller", "class: " + parent.getName());
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
		// TODO Auto-generated constructor stub
	}

	public PianoHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		try {
			Class parent = this.getClass();
			do {
				parent = parent.getSuperclass();
			} while (!parent.getName().equals(
					"android.widget.HorizontalScrollView"));

			Log.i("Scroller", "class: " + parent.getName());
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
		// TODO Auto-generated constructor stub
	}

	public PianoHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		try {
			Class parent = this.getClass();
			do {
				parent = parent.getSuperclass();
			} while (!parent.getName().equals(
					"android.widget.HorizontalScrollView"));

			Log.i("Scroller", "class: " + parent.getName());
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
		// TODO Auto-generated constructor stub
	}

	private void init() {
		try {
			Class parent = this.getClass();
			do {
				parent = parent.getSuperclass();
			} while (!parent.getName().equals(
					"android.widget.HorizontalScrollView"));

			Log.i("Scroller", "class: " + parent.getName());
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

		/*
		 * int hauteur = (int) (getHeight() * proportionHauteur);
		 * 
		 * Log.d("passageScroll", String.valueOf(hauteur)); int ev =
		 * MotionEventCompat.getActionMasked(event);
		 * 
		 * int pointerIndex = MotionEventCompat.getActionIndex(event); int
		 * pointerId = event.getPointerId(pointerIndex); int y = (int)
		 * MotionEventCompat.getY(event, pointerIndex); int x = (int)
		 * MotionEventCompat.getX(event, pointerIndex);
		 * 
		 * 
		 * if (event.getEdgeFlags() != 0) { // Don't handle edge touches
		 * immediately -- they may actually belong // to one of our //
		 * descendants. return false; }
		 * 
		 * 
		 * 
		 * if (y > hauteur) { switch (event.getAction()) {
		 * 
		 * case MotionEvent.ACTION_DOWN:
		 * 
		 * // if we can scroll pass the event to the superclass return
		 * super.onTouchEvent(event); // only continue to handle the touch event
		 * if scrolling enabled
		 * 
		 * default:
		 * 
		 * return super.onTouchEvent(event); }
		 * 
		 * } return false;
		 * 
		 * }
		 * 
		 * public boolean onTouchEventBis(MotionEvent event) { int hauteur =
		 * (int) (getHeight() * proportionHauteur);
		 * 
		 * int ev = MotionEventCompat.getActionMasked(event);
		 * 
		 * int pointerIndex = MotionEventCompat.getActionIndex(event); int
		 * pointerId = event.getPointerId(pointerIndex); int y = (int)
		 * MotionEventCompat.getY(event, pointerIndex); int x = (int)
		 * MotionEventCompat.getX(event, pointerIndex); Log.d("OntoucheBis",
		 * String.valueOf(pointerId));
		 * 
		 * /* if (event.getEdgeFlags() != 0) { // Don't handle edge touches
		 * immediately -- they may actually belong // to one of our //
		 * descendants. return false; }
		 * 
		 * 
		 * switch (event.getAction()) {
		 * 
		 * case MotionEvent.ACTION_DOWN:
		 * 
		 * // if we can scroll pass the event to the superclass return
		 * super.onTouchEvent(event); // only continue to handle the touch event
		 * if scrolling enabled
		 * 
		 * default:
		 * 
		 * return super.onTouchEvent(event); }
		 */
		
		imageScroller.invalidate();
		return false;
	}

	public boolean onInterceptTouchEvent(MotionEvent event) {
		// Call super first because it does some hidden motion event handling
		boolean result = super.onInterceptTouchEvent(event);
		int hauteur = (int) (getHeight() * proportionHauteur);

		int ev = MotionEventCompat.getActionMasked(event);

		int pointerIndex = MotionEventCompat.getActionIndex(event);
		int pointerId = event.getPointerId(pointerIndex);
		int y = (int) MotionEventCompat.getY(event, pointerIndex);
		int x = (int) MotionEventCompat.getX(event, pointerIndex);

		// Now see if we are scrolling horizontally with the custom gesture
		// detector
		if (y > hauteur) {
			imageScroller.invalidate();
			return result;
		}
		// If not scrolling vertically (y<hauteur), don't hijack the event.
		else {
			imageScroller.invalidate();
			return false;
		}
		
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
		invalidate();
		imageScroller.invalidate();
	}

	public void customSmoothScrollTo(int x, int y) {
		customSmoothScrollBy(x - getScrollX(), y - getScrollY());
	}
	
	public void setImageScroller(ImageScroller imageScroller) {
		this.imageScroller=imageScroller;
	}

}
