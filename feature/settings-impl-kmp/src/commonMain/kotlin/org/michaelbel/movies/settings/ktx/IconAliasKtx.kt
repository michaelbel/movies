@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.settings.ktx

import android.content.Context
import org.michaelbel.movies.ui.appicon.IconAlias

expect fun IconAlias.iconSnackbarText(context: Context): String