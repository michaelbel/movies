package org.michaelbel.movies.ui.ktx

import coil3.compose.AsyncImagePainter

val AsyncImagePainter.State.isErrorOrEmpty: Boolean
    get() = this is AsyncImagePainter.State.Error || this is AsyncImagePainter.State.Empty