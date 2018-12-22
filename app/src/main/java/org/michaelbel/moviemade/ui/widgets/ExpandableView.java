package org.michaelbel.moviemade.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.moviemade.R;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class ExpandableView extends LinearLayout {

    private String title;

    private ViewGroup containerView;

    private AppCompatImageView arrowIcon;
    private TextView textViewTitle;

    private int innerViewRes;

    private FrameLayout card;

    public static final int DEFAULT_ANIM_DURATION = 350;
    private long animDuration = DEFAULT_ANIM_DURATION;

    private final static int COLLAPSING = 0;
    private final static int EXPANDING = 1;

    private boolean isExpanded = false;
    private boolean isExpanding = false;
    private boolean isCollapsing = false;
    private boolean expandOnClick = false;
    private boolean startExpanded = false;

    private int previousHeight = 0;

    public interface OnExpandedListener {
        void onExpandChanged(View v, boolean isExpanded);
    }

    private OnExpandedListener listener;

    private OnClickListener defaultClickListener = v -> {
        if(isExpanded()) collapse();
        else expand();
    };

    public ExpandableView(Context context) {
        super(context);
        initAttributes(context, null);
        initView(context);
    }

    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
        initView(context);
    }

    public ExpandableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expandable_view, this);
    }

    private void initAttributes(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableView);
        title = typedArray.getString(R.styleable.ExpandableView_title);
        innerViewRes = typedArray.getResourceId(R.styleable.ExpandableView_inner_view, View.NO_ID);
        expandOnClick = typedArray.getBoolean(R.styleable.ExpandableView_expandOnClick, true);
        animDuration = typedArray.getInteger(R.styleable.ExpandableView_animationDuration, DEFAULT_ANIM_DURATION);
        startExpanded = typedArray.getBoolean(R.styleable.ExpandableView_startExpanded, false);
        typedArray.recycle();
    }

    public void setInnerViewRes(@LayoutRes int layoutId) {
        innerViewRes = layoutId;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        arrowIcon = findViewById(R.id.arrow);
        textViewTitle = findViewById(R.id.title);

        if(!TextUtils.isEmpty(title)) textViewTitle.setText(title);

        card = findViewById(R.id.expand_layout);

        setInnerView(innerViewRes);

        containerView = findViewById(R.id.viewContainer);

        if(startExpanded){
            setAnimDuration(0);
            expand();
            setAnimDuration(animDuration);
        }

        if (expandOnClick) {
            card.setOnClickListener(defaultClickListener);
            //arrowIcon.setOnClickListener(defaultClickListener);
        }
    }

    public void expand() {
        final int initialHeight = card.getHeight();

        if(!isMoving()) {
            previousHeight = initialHeight;
        }

        card.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int targetHeight = card.getMeasuredHeight();

        if (targetHeight - initialHeight != 0) {
            animateViews(initialHeight, targetHeight - initialHeight, EXPANDING);
        }
    }

    public void collapse() {
        int initialHeight = card.getMeasuredHeight();

        if(initialHeight - previousHeight != 0) {
            animateViews(initialHeight,
                    initialHeight - previousHeight,
                    COLLAPSING);
        }
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    private void animateViews(final int initialHeight, final int distance, final int animationType){
        Animation expandAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1){
                    isExpanding = false;
                    isCollapsing = false;

                    if(listener != null){
                        if(animationType == EXPANDING){
                            listener.onExpandChanged(card,true);
                        }
                        else{
                            listener.onExpandChanged(card,false);
                        }
                    }
                }

                card.getLayoutParams().height = animationType == EXPANDING ? (int) (initialHeight + (distance * interpolatedTime)) :
                        (int) (initialHeight  - (distance * interpolatedTime));
                card.findViewById(R.id.viewContainer).requestLayout();

                containerView.getLayoutParams().height = animationType == EXPANDING ? (int) (initialHeight + (distance * interpolatedTime)) :
                        (int) (initialHeight  - (distance * interpolatedTime));

            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        RotateAnimation arrowAnimation = animationType == EXPANDING ?
                new RotateAnimation(0,180,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f) :
                new RotateAnimation(180,0,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        arrowAnimation.setFillAfter(true);

        arrowAnimation.setDuration(animDuration);
        expandAnimation.setDuration(animDuration);

        isExpanding = animationType == EXPANDING;
        isCollapsing = animationType == COLLAPSING;

        startAnimation(expandAnimation);
        arrowIcon.startAnimation(arrowAnimation);
        isExpanded = animationType == EXPANDING;
    }

    private boolean isExpanding(){
        return isExpanding;
    }

    private boolean isCollapsing(){
        return isCollapsing;
    }

    private boolean isMoving(){
        return isExpanding() || isCollapsing();
    }

    public void setOnExpandedListener(OnExpandedListener listener) {
        this.listener = listener;
    }

    public void setTitle(String title){
        if(textViewTitle != null) textViewTitle.setText(title);
    }

    public void setTitle(int resId){
        if (textViewTitle != null) textViewTitle.setText(resId);
    }

    private void setInnerView(int resId){
        ViewStub stub = findViewById(R.id.viewStub);
        stub.setLayoutResource(resId);
        View innerView = stub.inflate();
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        /*if (arrowIcon != null) {
            arrowIcon.setOnClickListener(l);
        }*/
        super.setOnClickListener(l);
    }

    public void setAnimDuration(long animDuration) {
        this.animDuration = animDuration;
    }
}