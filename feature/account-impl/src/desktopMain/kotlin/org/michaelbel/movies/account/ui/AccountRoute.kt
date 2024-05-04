package org.michaelbel.movies.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo

@Composable
fun AccountRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AccountScreenContent(
        account = AccountPojo.Empty,
        loading = false,
        onBackClick = onBackClick,
        onLogoutClick = {},
        modifier = modifier
    )
}