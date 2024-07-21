package org.michaelbel.movies.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AccountRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    //viewModel: AccountViewModel = koinInject<AccountViewModel>()
) {
    /*val account by viewModel.account.collectAsStateCommon()

    AccountScreenContent(
        account = account.orEmpty,
        loading = viewModel.loading,
        onBackClick = onBackClick,
        onLogoutClick = { viewModel.onLogoutClick(onBackClick) },
        modifier = modifier
    )*/
}