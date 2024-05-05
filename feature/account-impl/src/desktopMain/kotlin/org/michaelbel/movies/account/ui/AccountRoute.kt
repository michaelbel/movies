package org.michaelbel.movies.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import org.michaelbel.movies.account.AccountViewModel
import org.michaelbel.movies.persistence.database.ktx.orEmpty

@Composable
fun AccountRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = koinInject<AccountViewModel>()
) {
    val account by viewModel.account.collectAsState()

    AccountScreenContent(
        account = account.orEmpty,
        loading = viewModel.loading,
        onBackClick = onBackClick,
        onLogoutClick = { viewModel.onLogoutClick(onBackClick) },
        modifier = modifier
    )
}