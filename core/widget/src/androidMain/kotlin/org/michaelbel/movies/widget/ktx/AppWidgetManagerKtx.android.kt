package org.michaelbel.movies.widget.ktx

import android.appwidget.AppWidgetManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberAndPinAppWidgetProvider(): () -> Unit {
    if (Build.VERSION.SDK_INT < 26) return {}
    val context = LocalContext.current
    val appWidgetManager = remember { AppWidgetManager.getInstance(context) }
    val appWidgetProvider = remember { appWidgetManager.getInstalledProvidersForPackage(context.packageName, null).firstOrNull() }
    return { appWidgetProvider?.pin(context) ?: run {} }
}