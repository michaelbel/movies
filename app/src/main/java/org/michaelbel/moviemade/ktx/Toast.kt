@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.app.Activity
import android.content.Context
import android.view.Gravity.CENTER
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun Context.toast(message: String? = null, @StringRes messageRes: Int = 0, gravity: Int = CENTER, length: Int = Toast.LENGTH_SHORT) {
    val toastMessage: String? = if (!message.isNullOrEmpty()) message else if (messageRes != 0) getString(messageRes) else null
    if (toastMessage != null) {
        val toast = Toast.makeText(this, toastMessage, length)
        val view: TextView = toast.view.findViewById(android.R.id.message) as TextView
        view.gravity = gravity
        toast.show()
    }
}

inline fun Fragment.toast(message: String? = null, gravity: Int = CENTER, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(message, 0, gravity, length)
}

inline fun Activity.toast(message: String? = null, gravity: Int = CENTER, length: Int = Toast.LENGTH_SHORT) {
    applicationContext.toast(message, 0, gravity, length)
}

inline fun Context.toast(@StringRes messageRes: Int, gravity: Int = CENTER, length: Int = Toast.LENGTH_SHORT) {
    toast(null, messageRes, gravity, length)
}

inline fun Fragment.toast(@StringRes messageRes: Int, gravity: Int = CENTER, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(null, messageRes, gravity, length)
}

inline fun Activity.toast(@StringRes messageRes: Int, gravity: Int = CENTER, length: Int = Toast.LENGTH_SHORT) {
    applicationContext.toast(null, messageRes, gravity, length)
}