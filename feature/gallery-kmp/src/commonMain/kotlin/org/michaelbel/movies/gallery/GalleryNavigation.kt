@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.gallery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.michaelbel.movies.gallery.ui.GalleryRoute

expect fun NavController.navigateToGallery(movieId: Int)

expect fun NavGraphBuilder.galleryGraph(
    navigateBack: () -> Unit,
)