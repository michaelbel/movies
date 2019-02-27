package org.michaelbel.moviemade.presentation.common

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_error.view.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.utils.Error
import org.michaelbel.moviemade.core.utils.ViewUtil

class ErrorView: LinearLayout {

    private var errorListener: ErrorListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_error, this)
        button_reload.setOnClickListener {
            if (errorListener != null) {
                errorListener!!.onReloadData()
            }
        }
    }

    fun setError(error: Throwable, code: Int) {
        when (code) {
            Error.ERR_NO_CONNECTION -> {
                setTitle(R.string.error_offline)
                setMessage(R.string.msg_check_internet)
            }
            Error.ERR_NO_MOVIES -> {
                setIcon(R.drawable.ic_movie)
                setTitle(R.string.error_no_movies)
            }
            401 -> {

            }
            404 -> {

            }
        }
    }

    fun setErrorListener(errorListener: ErrorListener) {
        this.errorListener = errorListener
    }

    private fun setIcon(icon: Int) {
        error_icon.visibility = VISIBLE
        error_icon.setImageDrawable(ViewUtil.getIcon(context, icon, ContextCompat.getColor(context, R.color.errorColor)))
    }

    private fun setTitle(@StringRes textId: Int) {
        error_title.visibility = VISIBLE
        error_title.setText(textId)
    }

    private fun setMessage(@StringRes textId: Int) {
        text_error_message.visibility = VISIBLE
        text_error_message.setText(textId)
    }

    interface ErrorListener {
        fun onReloadData()
    }
}