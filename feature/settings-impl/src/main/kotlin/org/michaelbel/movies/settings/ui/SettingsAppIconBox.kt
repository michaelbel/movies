package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings.ktx.backgroundColor
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.appicon.setIcon
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsAppIconBox(
    onAppIconChanged: (IconAlias) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    fun changeAppIcon(iconAlias: IconAlias) {
        onAppIconChanged(iconAlias)
        context.setIcon(iconAlias)
    }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (title, redBox, purpleBox, brownBox, amoledBox) = createRefs()

        Text(
            text = stringResource(R.string.settings_app_launcher_icon),
            modifier = Modifier.constrainAs(title) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top, 8.dp)
            },
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        AppIconBox(
            iconAlias = IconAlias.Red,
            modifier = Modifier
                .constrainAs(redBox) {
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(purpleBox.start, 4.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
                .fillMaxWidth(.20F)
                .clip(RoundedCornerShape(8.dp))
                .background(IconAlias.Red.backgroundColor(context))
                .clickable { changeAppIcon(IconAlias.Red) }
        )

        AppIconBox(
            iconAlias = IconAlias.Purple,
            modifier = Modifier
                .constrainAs(purpleBox) {
                    height = Dimension.wrapContent
                    start.linkTo(redBox.end, 4.dp)
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(brownBox.start, 4.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
                .fillMaxWidth(.20F)
                .clip(RoundedCornerShape(8.dp))
                .background(IconAlias.Purple.backgroundColor(context))
                .clickable { changeAppIcon(IconAlias.Purple) }
        )

        AppIconBox(
            iconAlias = IconAlias.Brown,
            modifier = Modifier
                .constrainAs(brownBox) {
                    height = Dimension.wrapContent
                    start.linkTo(purpleBox.end, 4.dp)
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(amoledBox.start, 4.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
                .fillMaxWidth(.20F)
                .clip(RoundedCornerShape(8.dp))
                .background(IconAlias.Brown.backgroundColor(context))
                .clickable { changeAppIcon(IconAlias.Brown) }
        )

        AppIconBox(
            iconAlias = IconAlias.Amoled,
            modifier = Modifier
                .constrainAs(amoledBox) {
                    height = Dimension.wrapContent
                    start.linkTo(brownBox.end, 4.dp)
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
                .fillMaxWidth(.20F)
                .clip(RoundedCornerShape(8.dp))
                .background(IconAlias.Amoled.backgroundColor(context))
                .clickable { changeAppIcon(IconAlias.Amoled) }
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsAppIconBoxPreview() {
    MoviesTheme {
        SettingsAppIconBox(
            onAppIconChanged = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun SettingsAppIconBoxAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingsAppIconBox(
            onAppIconChanged = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}