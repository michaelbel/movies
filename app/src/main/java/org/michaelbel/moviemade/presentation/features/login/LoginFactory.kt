package org.michaelbel.moviemade.presentation.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.michaelbel.domain.UsersRepository

@Suppress("unchecked_cast")
class LoginFactory(private val repository: UsersRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginModel(repository) as T
    }
}