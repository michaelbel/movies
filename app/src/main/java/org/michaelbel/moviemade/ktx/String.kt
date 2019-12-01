@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Patterns
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import timber.log.Timber
import java.util.*

inline fun String.append(text: String): String = "$this$text"

@Suppress("DEPRECATION")
inline val String.fromHtml: Spanned
    get() =  if (Build.VERSION.SDK_INT >= 24) Html.fromHtml(this, FROM_HTML_MODE_LEGACY) else Html.fromHtml(this)

inline val String.isHashtag: Boolean
    get() = startsWith("#")

inline val String.isMention: Boolean
    get() = startsWith("@")

inline val String.isEmail: Boolean
    get() = startsWith("mailto:")

inline val String.isLink: Boolean
    get() = startsWith("http")

fun Context.loadProperty(fileName: String, key: String): String? {
    try {
        val properties = Properties()
        val inputStream = assets.open(fileName)
        properties.load(inputStream)
        return properties.getProperty(key)
    } catch (e: Exception) {
        Timber.e(e)
    }

    return null
}

/**
 * Валидация текста на наличие только латиницы и кириллицы
 */
inline val CharSequence.isTextOnly: Boolean
    get() = isNotEmpty() && isNotBlank() && matches("[a-zA-Zа-яА-я]+".toRegex())

/**
 * Проверка валидность URL
 *
 * @param url проверяемая ссылка
 */
inline val String.isUrlValid: Boolean
    get() = !Patterns.WEB_URL.matcher(this).matches()