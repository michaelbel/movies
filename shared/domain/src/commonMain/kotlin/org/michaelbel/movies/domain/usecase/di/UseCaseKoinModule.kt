package org.michaelbel.movies.domain.usecase.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase
import org.michaelbel.movies.domain.usecase.AccountExpireTimeUseCase
import org.michaelbel.movies.domain.usecase.AccountIdUseCase
import org.michaelbel.movies.domain.usecase.AccountPojoFlowUseCase
import org.michaelbel.movies.domain.usecase.BiometricEnabledFlowUseCase
import org.michaelbel.movies.domain.usecase.CreateRequestTokenUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionWithLoginUseCase
import org.michaelbel.movies.domain.usecase.CurrentFeedViewFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentMovieListFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentThemeFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentTraktListFlowUseCase
import org.michaelbel.movies.domain.usecase.DeleteSessionUseCase
import org.michaelbel.movies.domain.usecase.FavoriteMoviesPagingDataUseCase
import org.michaelbel.movies.domain.usecase.FetchAndInsertSearchMoviesUseCase
import org.michaelbel.movies.domain.usecase.ImagesFlowUseCase
import org.michaelbel.movies.domain.usecase.ImagesUseCase
import org.michaelbel.movies.domain.usecase.InsertMovieUseCase
import org.michaelbel.movies.domain.usecase.InsertMoviesUseCase
import org.michaelbel.movies.domain.usecase.InsertPagingKeyUseCase
import org.michaelbel.movies.domain.usecase.IsBiometricEnabledUseCase
import org.michaelbel.movies.domain.usecase.MovieDetailsUseCase
import org.michaelbel.movies.domain.usecase.MovieFlowUseCase
import org.michaelbel.movies.domain.usecase.MovieUseCase
import org.michaelbel.movies.domain.usecase.MoviesFlowUseCase
import org.michaelbel.movies.domain.usecase.MoviesPagingDataUseCase
import org.michaelbel.movies.domain.usecase.MoviesResultUseCase
import org.michaelbel.movies.domain.usecase.MoviesWidgetUseCase
import org.michaelbel.movies.domain.usecase.NotificationExpireTimeUseCase
import org.michaelbel.movies.domain.usecase.PagingKeyPageUseCase
import org.michaelbel.movies.domain.usecase.PagingKeyPrevPageUseCase
import org.michaelbel.movies.domain.usecase.PagingKeyTotalPagesUseCase
import org.michaelbel.movies.domain.usecase.ProviderFlowUseCase
import org.michaelbel.movies.domain.usecase.RemoveMovieUseCase
import org.michaelbel.movies.domain.usecase.RemoveMoviesUseCase
import org.michaelbel.movies.domain.usecase.RemovePagingKeyUseCase
import org.michaelbel.movies.domain.usecase.ResetNotificationExpireTimeUseCase
import org.michaelbel.movies.domain.usecase.ResetSettingsUseCase
import org.michaelbel.movies.domain.usecase.ScreenshotBlockEnabledFlowUseCase
import org.michaelbel.movies.domain.usecase.SearchMoviesPagingDataUseCase
import org.michaelbel.movies.domain.usecase.SearchMoviesResultUseCase
import org.michaelbel.movies.domain.usecase.SelectFeedViewUseCase
import org.michaelbel.movies.domain.usecase.SelectMovieListUseCase
import org.michaelbel.movies.domain.usecase.SelectThemeUseCase
import org.michaelbel.movies.domain.usecase.SelectTraktListUseCase
import org.michaelbel.movies.domain.usecase.SetBiometricEnabledUseCase
import org.michaelbel.movies.domain.usecase.SetDynamicColorsUseCase
import org.michaelbel.movies.domain.usecase.SetPaletteColorsUseCase
import org.michaelbel.movies.domain.usecase.SetPaletteKeyUseCase
import org.michaelbel.movies.domain.usecase.SetProviderUseCase
import org.michaelbel.movies.domain.usecase.SetScreenshotBlockEnabledUseCase
import org.michaelbel.movies.domain.usecase.SetSeedColorUseCase
import org.michaelbel.movies.domain.usecase.SuggestionPojosFlowUseCase
import org.michaelbel.movies.domain.usecase.ThemeDataFlowUseCase
import org.michaelbel.movies.domain.usecase.TraktMoviesPagingDataUseCase
import org.michaelbel.movies.domain.usecase.TraktMoviesResultUseCase
import org.michaelbel.movies.domain.usecase.TraktPagingKeyPageUseCase
import org.michaelbel.movies.domain.usecase.UpdateFavoriteUseCase
import org.michaelbel.movies.domain.usecase.UpdateNotificationExpireTimeUseCase
import org.michaelbel.movies.domain.usecase.UpdateSuggestionsUseCase
import org.michaelbel.movies.network.di.networkKoinModule
import org.michaelbel.movies.persistence.database.MoviesDatabase
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.dao.ImageDao
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.SuggestionDao
import org.michaelbel.movies.persistence.database.di.moviesDatabaseKoinModule
import org.michaelbel.movies.persistence.database.di.persistenceKoinModule
import org.michaelbel.movies.persistence.datastore.di.moviesPreferencesKoinModule
import org.michaelbel.movies.persistence.datastore.di.tmdbPreferencesKoinModule
import org.michaelbel.movies.persistence.datastore.di.traktPreferencesKoinModule

val useCaseKoinModule = module {
    includes(
        dispatchersKoinModule,
        moviesAnalyticsKoinModule,
        moviesDatabaseKoinModule,
        moviesPreferencesKoinModule,
        tmdbPreferencesKoinModule,
        traktPreferencesKoinModule,
        persistenceKoinModule,
        networkKoinModule
    )
    single<SuggestionDao> { get<MoviesDatabase>().suggestionDao }
    single<ImageDao> { get<MoviesDatabase>().imageDao }
    single<MovieDao> { get<MoviesDatabase>().movieDao }
    single<AccountDao> { get<MoviesDatabase>().accountDao }
    singleOf(::SuggestionPojosFlowUseCase)
    singleOf(::ImagesFlowUseCase)
    singleOf(::ImagesUseCase)
    singleOf(::MovieDetailsUseCase)
    singleOf(::MovieFlowUseCase)
    singleOf(::MovieUseCase)
    singleOf(::MoviesFlowUseCase)
    singleOf(::MoviesWidgetUseCase)
    singleOf(::AccountPojoFlowUseCase)
    singleOf(::AccountIdUseCase)
    singleOf(::AccountExpireTimeUseCase)
    singleOf(::AccountDetailsUseCase)
    singleOf(::BiometricEnabledFlowUseCase)
    singleOf(::CreateRequestTokenUseCase)
    singleOf(::CreateSessionUseCase)
    singleOf(::CreateSessionWithLoginUseCase)
    singleOf(::CurrentFeedViewFlowUseCase)
    singleOf(::CurrentMovieListFlowUseCase)
    singleOf(::CurrentThemeFlowUseCase)
    singleOf(::CurrentTraktListFlowUseCase)
    singleOf(::DeleteSessionUseCase)
    singleOf(::FavoriteMoviesPagingDataUseCase)
    singleOf(::FetchAndInsertSearchMoviesUseCase)
    singleOf(::InsertMovieUseCase)
    singleOf(::InsertMoviesUseCase)
    singleOf(::InsertPagingKeyUseCase)
    singleOf(::IsBiometricEnabledUseCase)
    singleOf(::MoviesPagingDataUseCase)
    singleOf(::MoviesResultUseCase)
    singleOf(::NotificationExpireTimeUseCase)
    singleOf(::PagingKeyPageUseCase)
    singleOf(::PagingKeyPrevPageUseCase)
    singleOf(::PagingKeyTotalPagesUseCase)
    singleOf(::ProviderFlowUseCase)
    singleOf(::RemoveMovieUseCase)
    singleOf(::RemoveMoviesUseCase)
    singleOf(::RemovePagingKeyUseCase)
    singleOf(::ResetNotificationExpireTimeUseCase)
    singleOf(::ResetSettingsUseCase)
    singleOf(::ScreenshotBlockEnabledFlowUseCase)
    singleOf(::SearchMoviesPagingDataUseCase)
    singleOf(::SearchMoviesResultUseCase)
    singleOf(::SelectFeedViewUseCase)
    singleOf(::SelectMovieListUseCase)
    singleOf(::SelectThemeUseCase)
    singleOf(::SelectTraktListUseCase)
    singleOf(::SetBiometricEnabledUseCase)
    singleOf(::SetDynamicColorsUseCase)
    singleOf(::SetPaletteColorsUseCase)
    singleOf(::SetPaletteKeyUseCase)
    singleOf(::SetProviderUseCase)
    singleOf(::SetScreenshotBlockEnabledUseCase)
    singleOf(::SetSeedColorUseCase)
    singleOf(::ThemeDataFlowUseCase)
    singleOf(::TraktMoviesPagingDataUseCase)
    singleOf(::TraktMoviesResultUseCase)
    singleOf(::TraktPagingKeyPageUseCase)
    singleOf(::UpdateFavoriteUseCase)
    singleOf(::UpdateNotificationExpireTimeUseCase)
    singleOf(::UpdateSuggestionsUseCase)
}
