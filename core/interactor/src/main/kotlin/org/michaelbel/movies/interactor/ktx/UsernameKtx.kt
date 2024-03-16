package org.michaelbel.movies.interactor.ktx

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import org.michaelbel.movies.interactor.entity.Username

val Username.isNotEmpty: Boolean
    get() = value.isNotEmpty()

val Username.trim: Username
    get() = Username(value.trim())

val UsernameSaver: Saver<MutableState<Username>, String>
    get() = Saver(
        save = { it.value.value },
        restore = { mutableStateOf(Username(it)) }
    )