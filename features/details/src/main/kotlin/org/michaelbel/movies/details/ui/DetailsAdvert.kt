package org.michaelbel.movies.details.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import org.michaelbel.movies.details.R
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun DetailsAdvert(
    modifier: Modifier = Modifier,
    adRequest: AdRequest
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    adSize = AdSize.LARGE_BANNER
                    adUnitId = context.getString(R.string.admobBannerTestUnitId)
                    loadAd(adRequest)
                }
            },
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailsAdvertPreview() {
    MoviesTheme {
        DetailsAdvert(
            modifier = Modifier
                .fillMaxSize(),
            adRequest = AdRequest.Builder().build()
        )
    }
}