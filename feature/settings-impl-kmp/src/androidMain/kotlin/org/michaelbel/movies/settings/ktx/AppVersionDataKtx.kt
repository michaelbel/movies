@file:Suppress("DEPRECATION")

package org.michaelbel.movies.settings.ktx

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import org.michaelbel.movies.settings_impl_kmp.BuildConfig

private val Context.packageInfo: PackageInfo
    get() {
        return if (Build.VERSION.SDK_INT >= 33) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0L))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }
    }

internal val Context.versionName: String
    get() = packageInfo.versionName

internal val Context.versionCode: Long
    get() = if (Build.VERSION.SDK_INT >= 28) packageInfo.longVersionCode else packageInfo.versionCode.toLong()

internal val isDebug: Boolean
    get() = BuildConfig.DEBUG