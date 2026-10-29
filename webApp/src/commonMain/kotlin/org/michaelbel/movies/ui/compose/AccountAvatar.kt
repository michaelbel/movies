@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.letters

@Composable
fun AccountAvatar(
    account: AccountPojo,
    fontSize: TextUnit,
    modifier: Modifier
) {
    when {
        account.avatarUrl.isNotEmpty() -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(account.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Account Avatar Image",
                modifier = modifier.clip(MaterialShapes.Cookie9Sided.toShape()),
                contentScale = ContentScale.Crop
            )
        }
        else -> {
            Box(
                modifier = modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = MaterialShapes.Cookie9Sided.toShape()
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = account.letters.uppercase(),
                    style = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = fontSize
                    )
                )
            }
        }
    }
}
