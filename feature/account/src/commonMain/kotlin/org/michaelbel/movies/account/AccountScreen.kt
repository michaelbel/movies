@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.account.intent.AccountIntent
import org.michaelbel.movies.account.model.AccountModel
import org.michaelbel.movies.account.preview.AccountModelPreviewParameterProvider
import org.michaelbel.movies.persistence.database.ktx.letters
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.compose.AccountAvatar
import org.michaelbel.movies.ui.icons.Adult
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.isNavigationBar
import org.michaelbel.movies.ui.preview.wrapper.ThemeWrapper
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsStateCommon()

    AccountScreenContent(
        state = state,
        dispatch = viewModel::dispatch
    )
}

@Composable
private fun AccountScreenContent(
    state: AccountModel,
    dispatch: (AccountIntent) -> Unit
) {
    val buttonContainerColor = MaterialTheme.colorScheme.surfaceTint
    val buttonContentColor = contentColorFor(buttonContainerColor).let { color ->
        if (color == Color.Unspecified) MaterialTheme.colorScheme.onSurface else color
    }

    Column(
        modifier = Modifier
            .padding(horizontal = if (isNavigationBar) 16.dp else 64.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(MoviesStrings.account_title),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                FilledIconButton(
                    onClick = { dispatch(AccountIntent.BackClick) },
                    shapes = IconButtonDefaults.shapes(
                        shape = IconButtonDefaults.smallRoundShape,
                        pressedShape = IconButtonDefaults.smallPressedShape
                    ),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = .08F),
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Image(
                        imageVector = MoviesIcons.Close,
                        contentDescription = stringResource(MoviesContentDescription.CloseIcon),
                        modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                AccountAvatar(
                    account = state.accountPojo,
                    fontSize = if (state.accountPojo.letters.length == 1) 32.sp else 26.sp,
                    modifier = Modifier.size(64.dp)
                )

                if (state.accountPojo.adult) {
                    Icon(
                        imageVector = MoviesIcons.Adult,
                        contentDescription = stringResource(MoviesContentDescription.AdultIcon),
                        modifier = Modifier
                            .size(IconButtonDefaults.smallIconSize)
                            .align(Alignment.BottomEnd)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = MaterialTheme.shapes.small
                            ),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 12.dp, end = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                if (state.accountPojo.name.isNotEmpty()) {
                    Text(
                        text = state.accountPojo.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }

                if (state.accountPojo.username.isNotEmpty()) {
                    Text(
                        text = state.accountPojo.username,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
        }

        if (state.accountPojo.country.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 16.dp, top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = MoviesIcons.LocationOn,
                    contentDescription = stringResource(MoviesContentDescription.UserLocationIcon),
                    modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = state.accountPojo.country,
                    modifier = Modifier.padding(start = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }

        Button(
            onClick = { dispatch(AccountIntent.LogoutClick) },
            shapes = ButtonDefaults.shapes(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp),
            enabled = !state.isLogoutJobActive,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonContainerColor,
                contentColor = buttonContentColor,
                disabledContainerColor = buttonContainerColor,
                disabledContentColor = buttonContentColor
            ),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            when {
                state.isLogoutJobActive -> {
                    LoadingIndicator(
                        modifier = Modifier.size(40.dp),
                        color = buttonContentColor
                    )
                }
                else -> {
                    Text(
                        text = stringResource(MoviesStrings.account_logout),
                        style = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}

@PreviewWrapper(ThemeWrapper::class)
@Preview
@Composable
private fun AccountScreenContentPreview(
    @PreviewParameter(AccountModelPreviewParameterProvider::class) state: AccountModel
) {
    AccountScreenContent(
        state = state,
        dispatch = {}
    )
}
