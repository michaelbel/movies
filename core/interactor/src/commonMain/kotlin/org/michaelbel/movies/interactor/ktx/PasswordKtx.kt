package org.michaelbel.movies.interactor.ktx

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import org.michaelbel.movies.interactor.entity.Password

val Password.isNotEmpty: Boolean
    get() = value.isNotEmpty()

val Password.trim: Password
    get() = Password(value.trim())

val PasswordSaver: Saver<MutableState<Password>, String>
    get() = Saver(
        save = { it.value.value },
        restore = { mutableStateOf(Password(it)) }
    )