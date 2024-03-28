package org.michaelbel.movies.repository.ktx

import android.os.Build

actual val defaultDynamicColorsEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= 31