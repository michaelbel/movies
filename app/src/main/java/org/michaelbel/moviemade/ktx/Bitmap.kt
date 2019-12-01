@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.content.Context
import android.graphics.*
import android.media.ThumbnailUtils
import android.os.Environment
import androidx.annotation.WorkerThread
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.min

// todo
inline fun Bitmap.toFile(): File? {
    val root: String = Environment.getExternalStorageDirectory().toString()

    val dir = File(root)
    dir.mkdirs()

    val name = "${System.currentTimeMillis()}.jpg" // todo 20190904_121503.jpg

    val file = File(dir, name)
    if (file.exists()) {
        file.delete()
    }

    try {
        val fileOutputStream = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        return file
    } catch (e: Exception) {
        Timber.e(e)
    }

    return null
}

inline fun Bitmap.saturationBitmap(saturation: Float): Bitmap {
    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(saturation)

    val colorFilter = ColorMatrixColorFilter(colorMatrix)

    val paint = Paint()
    paint.colorFilter = colorFilter

    val resultBitmap = copy(Bitmap.Config.ARGB_8888, true)

    val canvas = Canvas(resultBitmap)
    canvas.drawBitmap(resultBitmap, 0F, 0F, paint)

    return resultBitmap
}

inline fun Bitmap.brightBitmap(brightness: Float): Bitmap {
    val colorTransform = floatArrayOf(
            1F, 0F, 0F, 0F, brightness,
            0F, 1F, 0F, 0F, brightness,
            0F, 0F, 1F, 0F, brightness,
            0F, 0F, 0F, 1F, 0F
    )

    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(0F)
    colorMatrix.set(colorTransform)

    val colorFilter = ColorMatrixColorFilter(colorMatrix)

    val paint = Paint()
    paint.colorFilter = colorFilter

    val resultBitmap: Bitmap = copy(Bitmap.Config.ARGB_8888, true)

    val canvas = Canvas(resultBitmap)
    canvas.drawBitmap(resultBitmap, 0F, 0F, paint)

    return resultBitmap
}

inline fun Bitmap.contrastBitmap(contrast: Float): Bitmap {
    val colorTransform = floatArrayOf(
            contrast, 0F, 0F, 0F,
            0F, 0F, contrast, 0F,
            0F, 0F, 0F, 0F,
            contrast, 0F, 0F, 0F,
            0F, 0F, 1F, 0F)

    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(0F)
    colorMatrix.set(colorTransform)

    val colorFilter = ColorMatrixColorFilter(colorMatrix)

    val paint = Paint()
    paint.colorFilter = colorFilter

    val resultBitmap = copy(Bitmap.Config.ARGB_8888, true)
    val canvas = Canvas(resultBitmap)
    canvas.drawBitmap(resultBitmap, 0F, 0F, paint)

    return resultBitmap
}

/**
 * Сохранение изображения во временную папку для передачи между интентами
 */
@WorkerThread
@Throws(IOException::class)
inline fun Bitmap.saveTempBitmap(fileName: String, context: Context): File {
    val file = File(context.cacheDir, fileName)
    if (file.exists()) file.delete()
    file.createNewFile()

    val bos = ByteArrayOutputStream()
    bos.use {
        compress(Bitmap.CompressFormat.JPEG, 80, bos)
        val bitmapData = bos.toByteArray()
        val fos = FileOutputStream(file)
        fos.use {
            it.write(bitmapData)
        }
    }
    return file
}

inline fun ByteArray.toBitmap(): Bitmap? = BitmapFactory.decodeByteArray(this, 0, size)

inline fun Bitmap.circleCrop(ds: Int): Bitmap {
    val output = Bitmap.createBitmap(ds, ds, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    val rect = Rect(0, 0, ds, ds)

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle(ds / 2f, ds / 2f, ds / 2f, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(Bitmap.createScaledBitmap(this, ds, ds, false), rect, rect, paint)

    return output
}

inline fun Bitmap.cropCenter(): Bitmap {
    val dimension = min(width, height)
    return ThumbnailUtils.extractThumbnail(this, dimension, dimension)
}