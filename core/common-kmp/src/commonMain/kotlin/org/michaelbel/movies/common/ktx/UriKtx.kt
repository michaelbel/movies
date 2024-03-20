@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.common.ktx

import android.net.Uri

expect fun Uri?.orEmpty(): Uri