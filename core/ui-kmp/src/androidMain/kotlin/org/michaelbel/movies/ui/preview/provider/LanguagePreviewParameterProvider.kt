package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import org.michaelbel.movies.common.localization.model.AppLanguage

class LanguagePreviewParameterProvider: CollectionPreviewParameterProvider<AppLanguage>(AppLanguage.VALUES)