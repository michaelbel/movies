package org.michaelbel.movies.work

sealed interface WorkInfoState {
    data class Success(val result: String): WorkInfoState
    data class Failure(val result: String): WorkInfoState
    data object None: WorkInfoState
}