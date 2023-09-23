package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.interactor.Interactor
import javax.inject.Inject

class SelectThemeCase @Inject constructor(
    private val interactor: Interactor
) {
    suspend operator fun invoke(appTheme: AppTheme) {
        interactor.selectTheme(appTheme)
    }
}