package org.michaelbel.moviemade.presentation.features.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.michaelbel.domain.UsersRepository

@Suppress("unchecked_cast")
class UserFactory(private val repository: UsersRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(model: Class<T>): T {
        return UserModel(repository) as T
    }
}