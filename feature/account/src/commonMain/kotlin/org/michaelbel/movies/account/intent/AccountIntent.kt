package org.michaelbel.movies.account.intent

import org.michaelbel.movies.common.mvi.Intent

sealed interface AccountIntent: Intent {
    data object CollectAccountPojo: AccountIntent
    data object BackClick: AccountIntent
    data object LogoutClick: AccountIntent
    data object RefreshTraktAccount: AccountIntent
}
