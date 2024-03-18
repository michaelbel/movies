@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent

expect val Context.appNotificationSettingsIntent: Intent

expect fun Activity.resolveNotificationPreferencesIntent()