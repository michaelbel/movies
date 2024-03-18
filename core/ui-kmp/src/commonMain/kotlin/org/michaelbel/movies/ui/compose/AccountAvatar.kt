@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import org.michaelbel.movies.persistence.database.entity.AccountDb

@Composable
expect fun AccountAvatar(
    account: AccountDb,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
)