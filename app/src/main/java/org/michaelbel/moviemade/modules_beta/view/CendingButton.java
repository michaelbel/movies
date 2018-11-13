package org.michaelbel.moviemade.modules_beta.view;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.utils.ScreenUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class CendingButton extends FrameLayout {

    private static final int BUTTON_UP = 1;
    private static final int BUTTON_DOWN = 2;

    private boolean checkedState;

    private CardView ascendingCardView;
    private CardView descendingCardView;

    private ImageView ascendingIcon;
    private ImageView descendingIcon;

    private CardView cardView;
    private Rect rect = new Rect();

    public CendingButton(Context context) {
        super(context);

        SharedPreferences prefs = context.getSharedPreferences("mainconfig", AppCompatActivity.MODE_PRIVATE);
        checkedState = prefs.getBoolean("filter", true);

        cardView = new CardView(context);
        cardView.setCardElevation(0);
        cardView.setUseCompatPadding(true);
        cardView.setPreventCornerOverlap(false);
        cardView.setRadius(ScreenUtils.dp(3));
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        cardView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(cardView);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 30, 1, 1, 1, 1));
        cardView.addView(layout);

//--------------------------------------------------------------------------------------------------

        ascendingCardView = new CardView(context);
        ascendingCardView.setCardElevation(0);
        ascendingCardView.setUseCompatPadding(false);
        ascendingCardView.setPreventCornerOverlap(false);
        ascendingCardView.setRadius(ScreenUtils.dp(3));
        ascendingCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceButton(BUTTON_UP);
            }
        });
        ascendingCardView.setForeground(Extensions.selectableItemBackgroundBorderlessDrawable(context));
        ascendingCardView.setLayoutParams(LayoutHelper.makeLinear(42, LayoutHelper.MATCH_PARENT));
        layout.addView(ascendingCardView);

        ascendingIcon = new ImageView(context);
        ascendingIcon.setLayoutParams(LayoutHelper.makeFrame(26, 26, Gravity.CENTER));
        ascendingCardView.addView(ascendingIcon);

//--------------------------------------------------------------------------------------------------

        View div = new View(context);
        div.setBackgroundColor(ContextCompat.getColor(context, Theme.backgroundColor()));
        div.setLayoutParams(LayoutHelper.makeLinear(1, LayoutHelper.MATCH_PARENT));
        //layout.addView(div);

//--------------------------------------------------------------------------------------------------

        descendingCardView = new CardView(context);
        descendingCardView.setCardElevation(0);
        descendingCardView.setUseCompatPadding(false);
        descendingCardView.setPreventCornerOverlap(false);
        descendingCardView.setRadius(ScreenUtils.dp(3));
        descendingCardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceButton(BUTTON_DOWN);
            }
        });
        descendingCardView.setForeground(Extensions.selectableItemBackgroundBorderlessDrawable(context));
        descendingCardView.setLayoutParams(LayoutHelper.makeLinear(42, LayoutHelper.MATCH_PARENT));
        layout.addView(descendingCardView);

        descendingIcon = new ImageView(context);
        descendingIcon.setLayoutParams(LayoutHelper.makeFrame(26, 26, Gravity.CENTER));
        descendingCardView.addView(descendingIcon);

        if (checkedState) { // Ascending button selected.
            ascendingCardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
            ascendingIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_down, ContextCompat.getColor(context, Theme.backgroundColor())));

            descendingCardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
            descendingIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_up, ContextCompat.getColor(context, Theme.iconActiveColor())));
        } else { // Descending button selected.
            ascendingCardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.backgroundColor()));
            ascendingIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_down, ContextCompat.getColor(context, Theme.iconActiveColor())));

            descendingCardView.setCardBackgroundColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
            descendingIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_up, ContextCompat.getColor(context, Theme.backgroundColor())));
        }
    }

    private void choiceButton(int button) {
        if (button == BUTTON_UP) {
            if (!checkedState) {
                SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", AppCompatActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("filter", true);
                editor.apply();

                ascendingIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_down, ContextCompat.getColor(getContext(), Theme.backgroundColor())));
                descendingIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_up, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));

                ArgbEvaluator evaluator = new ArgbEvaluator();
                ObjectAnimator ascendingCardAnimator = ObjectAnimator.ofObject(ascendingCardView, "cardBackgroundColor", evaluator, 0, 0);
                ascendingCardAnimator.setObjectValues(ContextCompat.getColor(getContext(), Theme.foregroundColor()), ContextCompat.getColor(getContext(), Theme.primaryTextColor()));

                ObjectAnimator descendingAnimator = ObjectAnimator.ofObject(descendingCardView, "cardBackgroundColor", evaluator, 0, 0);
                descendingAnimator.setObjectValues(ContextCompat.getColor(getContext(), Theme.primaryTextColor()), ContextCompat.getColor(getContext(), Theme.foregroundColor()));

                AnimatorSet set = new AnimatorSet();
                set.playTogether(ascendingCardAnimator, descendingAnimator);
                set.setDuration(150);
                set.start();
            }
        } else if (button == BUTTON_DOWN) {
            if (checkedState) {
                SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", AppCompatActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("filter", false);
                editor.apply();

                ascendingIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_down, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
                descendingIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_up, ContextCompat.getColor(getContext(), Theme.backgroundColor())));

                ArgbEvaluator evaluator = new ArgbEvaluator();
                ObjectAnimator ascendingAnimator = ObjectAnimator.ofObject(ascendingCardView, "cardBackgroundColor", evaluator, 0, 0);
                ascendingAnimator.setObjectValues(ContextCompat.getColor(getContext(), Theme.primaryTextColor()), ContextCompat.getColor(getContext(), Theme.foregroundColor()));

                ObjectAnimator descendingAnimator = ObjectAnimator.ofObject(descendingCardView, "cardBackgroundColor", evaluator, 0, 0);
                descendingAnimator.setObjectValues(ContextCompat.getColor(getContext(), Theme.foregroundColor()), ContextCompat.getColor(getContext(), Theme.primaryTextColor()));

                AnimatorSet set = new AnimatorSet();
                set.playTogether(ascendingAnimator, descendingAnimator);
                set.setDuration(150);
                set.start();
            }
        }

        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", AppCompatActivity.MODE_PRIVATE);
        checkedState = prefs.getBoolean("filter", true);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (cardView.getForeground() != null) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                cardView.getForeground().setHotspot(event.getX(), event.getY());
            }
        }

        return super.onTouchEvent(event);
    }
}