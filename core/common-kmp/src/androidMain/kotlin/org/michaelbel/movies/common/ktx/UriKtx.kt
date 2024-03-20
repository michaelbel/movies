package org.michaelbel.movies.common.ktx

import android.net.Uri

actual fun Uri?.orEmpty(): Uri = this ?: Uri.EMPTY