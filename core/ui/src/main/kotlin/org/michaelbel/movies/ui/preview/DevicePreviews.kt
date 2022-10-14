package org.michaelbel.movies.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Day theme"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Night theme"
)
annotation class DevicePreviews