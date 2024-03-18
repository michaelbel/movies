package org.michaelbel.movies.interactor.ktx

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import org.michaelbel.movies.interactor.entity.Username

actual val Username.isNotEmpty: Boolean
    get() = value.isNotEmpty()

actual val Username.trim: Username
    get() = Username(value.trim())

actual val UsernameSaver: Saver<MutableState<Username>, String>
    get() = Saver(
        save = { it.value.value },
        restore = { mutableStateOf(Username(it)) }
    )