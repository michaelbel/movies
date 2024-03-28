@file:Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "NON_ACTUAL_MEMBER_DECLARED_IN_EXPECT_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING"
)

package org.michaelbel.movies.ui.appicon

import androidx.annotation.DrawableRes
import org.michaelbel.movies.ui_kmp.R

sealed class IconAlias(
    val key: String,
    @DrawableRes val iconRes: Int
) {

    data object Red: IconAlias("RedIcon", R.drawable.ic_launcher_icon_red)

    data object Purple: IconAlias("PurpleIcon", R.drawable.ic_launcher_icon_purple)

    data object Brown: IconAlias("BrownIcon", R.drawable.ic_launcher_icon_brown)

    data object Amoled: IconAlias("AmoledIcon", R.drawable.ic_launcher_icon_amoled)

    companion object {
        val VALUES = listOf(
            Red,
            Purple,
            Brown,
            Amoled
        )
    }
}