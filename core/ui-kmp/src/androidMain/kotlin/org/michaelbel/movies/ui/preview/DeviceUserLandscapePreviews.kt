package org.michaelbel.movies.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Day",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Night",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Day Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    widthDp = 800,
    heightDp = 360
)
@Preview(
    name = "Night Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 800,
    heightDp = 360
)
actual annotation class DeviceUserLandscapePreviews