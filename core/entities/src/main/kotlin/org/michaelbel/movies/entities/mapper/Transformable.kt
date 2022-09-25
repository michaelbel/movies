package org.michaelbel.movies.entities.mapper

interface Transformable<T> {
    fun transform(): T
}