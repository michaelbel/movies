package org.michaelbel.movies.persistence.database.ktx

fun Int?.orEmpty(): Int {
    return this ?: 0
}

fun Long?.orEmpty(): Long {
    return this ?: 0L
}

fun Float?.orEmpty(): Float {
    return this ?: 0F
}

fun Double?.orEmpty(): Double {
    return this ?: 0.0
}

fun Boolean?.orEmpty(): Boolean {
    return this ?: false
}