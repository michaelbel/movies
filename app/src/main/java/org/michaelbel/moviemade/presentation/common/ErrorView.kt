package org.michaelbel.moviemade.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_error.view.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.Error
import org.michaelbel.moviemade.core.ViewUtil

class ErrorView: LinearLayout {

    interface Listener {
        fun onReloadData()
    }

    private var errorListener: Listener? = null

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_error, this)
        buttonReload.setOnClickListener {
            if (errorListener != null) {
                errorListener?.onReloadData()
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

    fun setErrorListener(listener: Listener) {
        errorListener = listener
    }

    private fun setIcon(icon: Int) {
        errorIcon.visibility = VISIBLE
        errorIcon.setImageDrawable(ViewUtil.getIcon(context, icon, ContextCompat.getColor(context, R.color.errorColor)))
    }

    private fun setTitle(@StringRes textId: Int) {
        errorTitle.visibility = VISIBLE
        errorTitle.setText(textId)
    }

    private fun setMessage(@StringRes textId: Int) {
        textErrorMessage.visibility = VISIBLE
        textErrorMessage.setText(textId)
    }
}