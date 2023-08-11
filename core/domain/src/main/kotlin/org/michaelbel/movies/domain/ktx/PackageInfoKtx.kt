package org.michaelbel.movies.domain.ktx

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

val Context.packageInfo: PackageInfo
    get() {
        return if (Build.VERSION.SDK_INT >= 33) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0L))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }
    }

@Suppress("Deprecation")
val PackageInfo.code: Long
    get() = if (Build.VERSION.SDK_INT >= 28) longVersionCode else versionCode.toLong()