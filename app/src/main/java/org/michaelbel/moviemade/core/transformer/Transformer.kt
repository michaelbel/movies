package org.michaelbel.moviemade.core.transformer

import io.reactivex.ObservableTransformer

abstract class Transformer<T>: ObservableTransformer<T, T>