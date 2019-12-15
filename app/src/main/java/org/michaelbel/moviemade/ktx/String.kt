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

inline val String.isMailTo: Boolean
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
 */
inline val String.isUrlValid: Boolean
    get() = !Patterns.WEB_URL.matcher(this).matches()

/**
 * Splits by spaces, newlines, and tabs only
 */
val String.camelCased: String
    get() {
        val split = toLowerCase().split(' ', '\n', '\t').toMutableList()
        if (split.size > 1) {
            for (i in 1 until split.size) {
                if (split[i].length > 1) {
                    val charArray = split[i].toCharArray()
                    charArray[0] = charArray[0].toUpperCase()
                    split[i] = String(charArray)
                }
            }
        }
        return split.joinToString("")
    }

val String.containsLetters: Boolean
    get() = matches(".*[a-zA-Z].*".toRegex())

val String.containsNumbers: Boolean
    get() = matches(".*[0-9].*".toRegex())

/**
 * Does not allow whitespace or symbols
 * Allows empty string
 */
val String.isAlphanumeric: Boolean
    get() = matches("^[a-zA-Z0-9]*$".toRegex())

/**
 * Does not allow whitespace or symbols
 * Allows empty string
 */
val String.isAlphabetic: Boolean
    get() = matches("^[a-zA-Z]*$".toRegex())

/**
 * Does not allow whitespace or symbols
 * Allows empty string
 */
val String.isNumeric: Boolean
    get() = matches("^[0-9]*$".toRegex())

val String.isEmail: Boolean
    get() = matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}".toRegex())

/**
 * If there is more than one most common character, this returns the character that occurred first in the String
 */
val String.mostCommonCharacter: Char? get() {
    if (length == 0) return null
    val map = HashMap<Char, Int>()
    for (char in toCharArray()) map[char] = (map[char] ?: 0) + 1
    var maxEntry = map.entries.elementAt(0)
    for (entry in map) maxEntry = if (entry.value > maxEntry.value) entry else maxEntry
    return maxEntry.key
}