package org.michaelbel.moviemade.presentation.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.core.utils.ViewUtil;
import org.michaelbel.moviemade.core.utils.Error;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

public class ErrorView extends LinearLayout {

    private AppCompatImageView errorIcon;
    private AppCompatTextView titleText;
    private AppCompatTextView messageText;
    private AppCompatButton retryBtn;
    private ErrorListener errorListener;

    public ErrorView(Context context) {
        super(context);
        init();
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_error, this);
        errorIcon = findViewById(R.id.error_icon);
        titleText = findViewById(R.id.error_title);
        messageText = findViewById(R.id.text_error_message);
        retryBtn = findViewById(R.id.button_reload);
        retryBtn.setOnClickListener(v -> {
            if (errorListener != null) {
                errorListener.onReloadData();
            }
        });
    }

    public void setError(Throwable error, int code) {
        if (code == Error.ERR_NO_CONNECTION) {
            setTitle(R.string.error_offline);
            setMessage(R.string.msg_check_internet);
        } else if (code == Error.ERR_NO_MOVIES) {
            setIcon(R.drawable.ic_movie);
            setTitle(R.string.error_no_movies);
        } else if (code == 401) {

        } else if (code == 404) {

        }
    }

    public void setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    private void setIcon(int icon) {
        errorIcon.setVisibility(VISIBLE);
        errorIcon.setImageDrawable(ViewUtil.INSTANCE.getIcon(getContext(), icon, ContextCompat.getColor(getContext(), R.color.errorColor)));
    }

    private void setTitle(@StringRes int textId) {
        titleText.setVisibility(VISIBLE);
        titleText.setText(textId);
    }

    private void setMessage(@StringRes int textId) {
        messageText.setVisibility(VISIBLE);
        messageText.setText(textId);
    }

    public interface ErrorListener {
        void onReloadData();
    }
}