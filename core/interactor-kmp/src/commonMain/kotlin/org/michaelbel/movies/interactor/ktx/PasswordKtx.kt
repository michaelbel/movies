@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.interactor.ktx

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import org.michaelbel.movies.interactor.entity.Password

expect val Password.isNotEmpty: Boolean

expect val Password.trim: Password

expect val PasswordSaver: Saver<MutableState<Password>, String>