@file:OptIn(
    ExperimentalMaterial3ExpressiveApi::class,
    ExperimentalMaterial3Api::class
)

package org.michaelbel.movies.accountweb

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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.michaelbel.movies.domain.usecase.DeleteSessionUseCase
import org.michaelbel.movies.main.mainWebWindowWidth
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.letters
import org.michaelbel.movies.ui.compose.AccountAvatar
import org.michaelbel.movies.ui.icons.Adult
import org.michaelbel.movies.ui.icons.MoviesIcons

@Composable
fun AccountDialog(
    accountPojo: AccountPojo,
    onDismissRequest: () -> Unit,
    deleteSessionUseCase: DeleteSessionUseCase = koinInject()
) {
    val scope = rememberCoroutineScope()
    var logoutJob by remember { mutableStateOf<Job?>(null) }
    val isLogoutJobActive = logoutJob != null

    val buttonContainerColor = MaterialTheme.colorScheme.surfaceTint
    val buttonContentColor = contentColorFor(buttonContainerColor).let { color ->
        if (color == Color.Unspecified) MaterialTheme.colorScheme.onSurface else color
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = if (mainWebWindowWidth < 840.dp) 16.dp else 64.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small
                )
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Account",
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    FilledIconButton(
                        onClick = onDismissRequest,
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
                            contentDescription = "Close Icon",
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
                        account = accountPojo,
                        fontSize = if (accountPojo.letters.length == 1) 32.sp else 26.sp,
                        modifier = Modifier.size(64.dp)
                    )

                    if (accountPojo.adult) {
                        Icon(
                            imageVector = MoviesIcons.Adult,
                            contentDescription = "Adult Icon",
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
                    if (accountPojo.name.isNotEmpty()) {
                        Text(
                            text = accountPojo.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }

                    if (accountPojo.username.isNotEmpty()) {
                        Text(
                            text = accountPojo.username,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                }
            }

            if (accountPojo.country.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = MoviesIcons.LocationOn,
                        contentDescription = "User Location Icon",
                        modifier = Modifier.size(IconButtonDefaults.smallIconSize),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Text(
                        text = accountPojo.country,
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
                onClick = {
                    val job = scope.launch {
                        try {
                            deleteSessionUseCase(Unit).getOrThrow()
                            onDismissRequest()
                        } catch (exception: CancellationException) {
                            throw exception
                        } catch (_: Exception) {
                            // Mirrors desktop: failed logout only stops the loading state.
                        }
                    }
                    logoutJob = job
                    job.invokeOnCompletion { logoutJob = null }
                },
                shapes = ButtonDefaults.shapes(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp),
                enabled = !isLogoutJobActive,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonContainerColor,
                    contentColor = buttonContentColor,
                    disabledContainerColor = buttonContainerColor,
                    disabledContentColor = buttonContentColor
                ),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                when {
                    isLogoutJobActive -> {
                        LoadingIndicator(
                            modifier = Modifier.size(40.dp),
                            color = buttonContentColor
                        )
                    }
                    else -> {
                        Text(
                            text = "Log out",
                            style = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}
