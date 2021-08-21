@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

inline val File.toBitmap: Bitmap
    get() = BitmapFactory.decodeFile(absolutePath)

// todo Add saveToGallery
// todo Add saveToDownloads
// todo Add saveToSdCard