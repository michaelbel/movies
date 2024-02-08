package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings.ktx.backgroundColor
import org.michaelbel.movies.settings.ktx.iconText
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.appicon.isEnabled
import org.michaelbel.movies.ui.ktx.context
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.IconAliasPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun AppIconBox(
    iconAlias: IconAlias,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (icon, radio, text) = createRefs()
        createHorizontalChain(radio, text, chainStyle = ChainStyle.Packed)

        Icon(
            painter = painterResource(iconAlias.iconRes),
            contentDescription = stringResource(MoviesContentDescription.AppIcon),
            modifier = Modifier
                .constrainAs(icon) {
                    width = Dimension.value(56.dp)
                    height = Dimension.value(56.dp)
                    start.linkTo(parent.start, 8.dp)
                    top.linkTo(parent.top, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                }
                .clip(CircleShape),
            tint = Color.Unspecified
        )

        RadioButton(
            selected = context.isEnabled(iconAlias),
            onClick = null,
            modifier = Modifier.constrainAs(radio) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start)
                top.linkTo(icon.bottom, 8.dp)
                end.linkTo(text.start)
                bottom.linkTo(parent.bottom, 8.dp)
            }
        )

        Text(
            text = iconAlias.iconText,
            modifier = Modifier
                .constrainAs(text) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(radio.end)
                    top.linkTo(radio.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(radio.bottom)
                }
                .padding(start = 2.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    }
}

@Composable
@DevicePreviews
private fun AppIconBoxPreview(
    @PreviewParameter(IconAliasPreviewParameterProvider::class) iconAlias: IconAlias
) {
    MoviesTheme {
        AppIconBox(
            iconAlias = iconAlias,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(iconAlias.backgroundColor(context))
        )
    }
}

@Composable
@Preview
private fun AppIconBoxAmoledPreview(
    @PreviewParameter(IconAliasPreviewParameterProvider::class) iconAlias: IconAlias
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        AppIconBox(
            iconAlias = iconAlias,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(iconAlias.backgroundColor(context))
        )
    }
}