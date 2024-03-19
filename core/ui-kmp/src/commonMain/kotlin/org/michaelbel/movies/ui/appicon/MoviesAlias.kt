@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.appicon

import android.content.Context

expect fun Context.isEnabled(iconAlias: IconAlias): Boolean

expect fun Context.setIcon(iconAlias: IconAlias)

expect fun Context.installLauncherIcon()