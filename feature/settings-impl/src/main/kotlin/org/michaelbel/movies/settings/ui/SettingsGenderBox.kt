package org.michaelbel.movies.settings.ui

import android.app.GrammaticalInflectionManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings.ktx.genderText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingsGenderBox(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val grammaticalInflectionManager by remember { mutableStateOf(context.getSystemService(GrammaticalInflectionManager::class.java)) }
    val grammaticalGender by remember { mutableStateOf(grammaticalInflectionManager.applicationGrammaticalGender) }
    val currentGrammaticalGender by remember { mutableStateOf(GrammaticalGender.transform(grammaticalGender)) }

    var genderDialog by remember { mutableStateOf(false) }

    if (genderDialog) {
        SettingsGenderDialog(
            onDismissRequest = {
                genderDialog = false
            }
        )
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable {
                genderDialog = true
            }
            .testTag("ConstraintLayout")
    ) {
        val (title, value) = createRefs()

        Text(
            text = stringResource(R.string.settings_gender),
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("TitleText"),
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        Text(
            text = currentGrammaticalGender.genderText,
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("ValueText"),
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsGenderBoxPreview() {
    MoviesTheme {
        SettingsGenderBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun SettingsGenderBoxAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingsGenderBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}