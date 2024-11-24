package org.michaelbel.movies.settings.gender.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.settings.gender.GrammaticalGenderManager
import org.michaelbel.movies.settings.gender.impl.GrammaticalGenderManagerImpl

actual val grammaticalGenderKoinModule = module {
    singleOf(::GrammaticalGenderManagerImpl) { bind<GrammaticalGenderManager>() }
}