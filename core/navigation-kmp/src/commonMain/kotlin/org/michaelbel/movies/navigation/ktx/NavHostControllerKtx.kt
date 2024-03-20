@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.navigation.ktx

import android.os.Bundle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

expect fun NavHostController.addOnDestinationChangedListener(
    listener: (destination: NavDestination, arguments: Bundle?) -> Unit
)