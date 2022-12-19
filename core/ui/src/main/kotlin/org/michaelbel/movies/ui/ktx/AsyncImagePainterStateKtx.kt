package org.michaelbel.movies.ui.ktx

import coil.compose.AsyncImagePainter

val AsyncImagePainter.State.isErrorOrEmpty: Boolean
    get() = this is AsyncImagePainter.State.Error || this is AsyncImagePainter.State.Empty
