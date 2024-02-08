package org.michaelbel.movies.account.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.michaelbel.movies.account.AccountViewModel
import org.michaelbel.movies.account_impl.R
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.compose.AccountAvatar
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.isPortrait
import org.michaelbel.movies.ui.ktx.lettersTextFontSizeLarge
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun AccountRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val account: AccountDb? by viewModel.account.collectAsStateWithLifecycle()

    AccountScreenContent(
        account = account.orEmpty,
        loading = viewModel.loading,
        onBackClick = onBackClick,
        onLogoutClick = { viewModel.onLogoutClick(onBackClick) },
        modifier = modifier
    )
}

@Composable
private fun AccountScreenContent(
    account: AccountDb,
    loading: Boolean,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier
            .padding(horizontal = if (isPortrait) 16.dp else 64.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
    ) {
        val (
            toolbar,
            accountAvatar,
            nameColumn,
            nameText,
            usernameText,
            countryBox,
            adultIcon,
            logoutButton
        ) = createRefs()
        createVerticalChain(nameText, usernameText, chainStyle = ChainStyle.Packed)

        AccountToolbar(
            modifier = Modifier.constrainAs(toolbar) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            },
            onNavigationIconClick = onBackClick
        )

        AccountAvatar(
            account = account,
            fontSize = account.lettersTextFontSizeLarge,
            modifier = Modifier.constrainAs(accountAvatar) {
                width = Dimension.value(64.dp)
                height = Dimension.value(64.dp)
                start.linkTo(parent.start, 16.dp)
                top.linkTo(toolbar.bottom)
            }
        )

        if (account.adult) {
            Icon(
                painter = painterResource(MoviesIcons.AdultOutline),
                contentDescription = stringResource(MoviesContentDescription.AdultIcon),
                modifier = Modifier
                    .constrainAs(adultIcon) {
                        width = Dimension.value(24.dp)
                        height = Dimension.value(24.dp)
                        end.linkTo(accountAvatar.end)
                        bottom.linkTo(accountAvatar.bottom)
                    }
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Column(
            modifier = Modifier.constrainAs(nameColumn) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(accountAvatar.end, 12.dp)
                top.linkTo(accountAvatar.top)
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(accountAvatar.bottom)
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            if (account.name.isNotEmpty()) {
                Text(
                    text = account.name,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }

            if (account.username.isNotEmpty()) {
                Text(
                    text = account.username,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }

        if (account.country.isNotEmpty()) {
            AccountCountryBox(
                country = account.country,
                modifier = Modifier.constrainAs(countryBox) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(accountAvatar.bottom, 8.dp)
                }
            )
        }

        Button(
            onClick = onLogoutClick,
            modifier = Modifier.constrainAs(logoutButton) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(if (account.country.isNotEmpty()) countryBox.bottom else accountAvatar.bottom, 8.dp)
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            },
            enabled = !loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            ),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(R.string.account_logout),
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun AccountScreenContentPreview() {
    MoviesTheme {
        AccountScreenContent(
            account = AccountDb(
                accountId = 0,
                avatarUrl = "",
                language = "",
                country = "Finland",
                name = "Michael Bely",
                adult = true,
                username = "michaelbel"
            ),
            loading = false,
            onBackClick = {},
            onLogoutClick = {}
        )
    }
}

@Composable
@Preview
private fun AccountScreenContentAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        AccountScreenContent(
            account = AccountDb(
                accountId = 0,
                avatarUrl = "",
                language = "",
                country = "Finland",
                name = "Michael Bely",
                adult = true,
                username = "michaelbel"
            ),
            loading = false,
            onBackClick = {},
            onLogoutClick = {}
        )
    }
}