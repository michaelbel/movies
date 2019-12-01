@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.annotation.LayoutRes

inline fun ViewGroup.inflate(@LayoutRes layoutRes: Int, root: ViewGroup? = this): View = inflate(context, layoutRes, root)