@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

inline fun <reified T: Any> Activity.startActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()

    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T: Any> Activity.startActivity(options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()

    startActivity(intent, options)
}

/**
 * Starts a new activity by creating a new [Intent], calling [block] with
 * the new instance as receiver, then passing the intent to
 * [Activity.startActivityForResult].
 */
inline fun <reified T: Activity> Activity.startActivityForResult(requestCode: Int, block: Intent.() -> Unit = {}) {
    startActivityForResult(Intent(this, T::class.java).apply { block(this) }, requestCode)
}

inline fun <reified T: Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

/**
 * Hides the soft keyboard if it's visible by calling
 * [android.view.inputmethod.InputMethodManager.hideSoftInputFromWindow].
 */
inline fun Activity.hideKeyboard() {
    val focused = currentFocus ?: return
    focused.clearFocus()
    focused.windowToken?.let {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it, 0)
    }
}

@RequiresApi(21)
inline fun Activity.setStatusBarColor(@ColorRes colorResId: Int) {
    val color = ContextCompat.getColor(applicationContext, colorResId)

    if (Build.VERSION.SDK_INT >= 21) {
        with(window) {
            addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            clearFlags(FLAG_TRANSLUCENT_STATUS)
            statusBarColor = color
        }
    }
}