package org.michaelbel.application.ui.view.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.AppBarLayout;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import org.michaelbel.application.R;

@SuppressWarnings("all")
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

	public enum State {
		EXPANDED,
		COLLAPSED,
		IDLE
	}

	private State mCurrentState = State.IDLE;

	@Override
	public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
		if (i == 0) {
			if (mCurrentState != State.EXPANDED) {
				onStateChanged(appBarLayout, State.EXPANDED);
			}

			mCurrentState = State.EXPANDED;
		} else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
			if (mCurrentState != State.COLLAPSED) {
				onStateChanged(appBarLayout, State.COLLAPSED);
			}

			mCurrentState = State.COLLAPSED;
		} else {
			if (mCurrentState != State.IDLE) {
				onStateChanged(appBarLayout, State.IDLE);
			}

			mCurrentState = State.IDLE;
		}

		onOffsetChanged(mCurrentState, Math.abs(i / (float) appBarLayout.getTotalScrollRange()));
	}

	public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

	public abstract void onOffsetChanged(State state, float offset);

	public static int getStatusBarHeightPixel(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}

		return result;
	}

	public static int getActionBarHeightPixel(Context context) {
		TypedValue tv = new TypedValue();

		if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
		} else if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
			return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
		} else {
			return 0;
		}
	}

	public static Point getDisplayDimen(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}
}