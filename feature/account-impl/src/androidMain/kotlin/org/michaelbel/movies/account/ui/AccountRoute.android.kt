package org.michaelbel.movies.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.account.AccountViewModel
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.ui.ktx.collectAsStateCommon

@Composable
fun AccountRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = koinViewModel()
) {
    val account by viewModel.account.collectAsStateCommon()

    AccountScreenContent(
        account = account.orEmpty,
        loading = viewModel.loading,
        onBackClick = onBackClick,
        onLogoutClick = { viewModel.onLogoutClick(onBackClick) },
        modifier = modifier
    )
}