package org.michaelbel.movies.widget.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.widget.configure.AppWidgetConfigureViewModel
import org.michaelbel.movies.widget.work.MoviesGlanceWidgetWorker

val glanceKoinModule = module {
    includes(
        interactorKoinModule
    )
    viewModelOf(::AppWidgetConfigureViewModel)
    workerOf(::MoviesGlanceWidgetWorker)
}