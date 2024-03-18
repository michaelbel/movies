@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.interactor.ktx

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import org.michaelbel.movies.interactor.entity.Username

expect val Username.isNotEmpty: Boolean

expect val Username.trim: Username

expect val UsernameSaver: Saver<MutableState<Username>, String>