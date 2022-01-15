package org.michaelbel.moviemade.presentation.features.main.adapter

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}