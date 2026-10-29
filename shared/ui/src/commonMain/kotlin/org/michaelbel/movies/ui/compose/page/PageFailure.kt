package org.michaelbel.movies.ui.compose.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.clickableWithoutRipple
import org.michaelbel.movies.ui.compose.RotatingCookie12SidedBox
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.isNavigationRail
import org.michaelbel.movies.ui.preview.wrapper.ThemeWrapper
import org.michaelbel.movies.ui.strings.MoviesStrings

@Composable
fun PageFailure(
    contentPadding: PaddingValues = PaddingValues(),
    isButtonVisible: Boolean = false,
    onClick: () -> Unit = {},
    onButtonClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .clickableWithoutRipple(onClick),
        contentPadding = contentPadding + PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            RotatingCookie12SidedBox(
                modifier = Modifier.size(164.dp),
                color = MaterialTheme.colorScheme.error
            ) {
                Icon(
                    imageVector = MoviesIcons.Info,
                    contentDescription = MoviesContentDescription.None,
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        item {
            Text(
                text = stringResource(MoviesStrings.error_loading),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            )
        }
        if (isButtonVisible) {
            item {
                Button(
                    onClick = onButtonClick,
                    shapes = ButtonDefaults.shapes(),
                    modifier = Modifier.then(if (isNavigationRail) Modifier.wrapContentWidth() else Modifier.fillMaxWidth()),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceTint
                    ),
                    contentPadding = PaddingValues(horizontal = 24.dp)
                ) {
                    Text(
                        text = stringResource(MoviesStrings.error_check_internet_connectivity),
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
@Preview(showBackground = true)
@Composable
private fun PageFailurePreview() {
    PageFailure(
        isButtonVisible = true
    )
}
