package org.michaelbel.movies.settings.model

internal sealed interface ModalBottomSheetType {

    object Language: ModalBottomSheetType

    object Theme: ModalBottomSheetType
}