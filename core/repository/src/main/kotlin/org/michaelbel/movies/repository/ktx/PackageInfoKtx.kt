package org.michaelbel.movies.repository.ktx

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

internal val Context.packageInfo: PackageInfo
    get() {
        return if (Build.VERSION.SDK_INT >= 33) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0L))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }
    }

@Suppress("Deprecation")
internal val PackageInfo.code: Long
    get() = if (Build.VERSION.SDK_INT >= 28) longVersionCode else versionCode.toLong()