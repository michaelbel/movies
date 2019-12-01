@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyToClipboard(text: CharSequence) {
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(text, text)
    clipboardManager.setPrimaryClip(clipData)
}