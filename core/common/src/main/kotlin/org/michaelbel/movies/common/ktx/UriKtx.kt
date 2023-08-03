package org.michaelbel.movies.common.ktx

import android.net.Uri

fun Uri?.orEmpty(): Uri = this ?: Uri.EMPTY