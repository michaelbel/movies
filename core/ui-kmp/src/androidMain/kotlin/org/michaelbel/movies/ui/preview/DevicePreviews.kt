package org.michaelbel.movies.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Day theme",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Night theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class DevicePreviews