package org.michaelbel.moviemade.app.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ErrorItem(onRetryClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(180.dp)
            .width(120.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(12f))
    ) {
        IconButton(onClick = { onRetryClick() }) {
            Image(
                painter = painterResource(id = org.michaelbel.moviemade.R.drawable.ic_clear),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                contentDescription = null
            )
        }
        Text("Error")
    }
}