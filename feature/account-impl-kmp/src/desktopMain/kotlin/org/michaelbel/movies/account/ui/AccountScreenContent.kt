package org.michaelbel.movies.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AccountRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AccountScreenContent(
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun AccountScreenContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

}