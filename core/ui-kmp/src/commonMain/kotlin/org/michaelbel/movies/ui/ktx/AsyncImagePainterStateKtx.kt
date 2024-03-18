@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.ktx

import coil.compose.AsyncImagePainter

expect val AsyncImagePainter.State.isErrorOrEmpty: Boolean