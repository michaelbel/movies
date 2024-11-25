@file:Suppress("DEPRECATION")

package org.michaelbel.movies.common.ktx

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

private val Context.packageInfo: PackageInfo
    get() {
        return if (Build.VERSION.SDK_INT >= 33) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0L))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }
    }

val Context.versionName: String
    get() = packageInfo.versionName.orEmpty()

val Context.versionCode: Long
    get() = if (Build.VERSION.SDK_INT >= 28) packageInfo.longVersionCode else packageInfo.versionCode.toLong()