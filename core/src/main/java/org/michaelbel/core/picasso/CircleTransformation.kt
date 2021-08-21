package org.michaelbel.core.picasso

import android.graphics.*
import androidx.annotation.ColorInt

import com.squareup.picasso.Transformation
import kotlin.math.min

@Suppress("unused")
class CircleTransformation: Transformation {

    private var borderColor: Int = 0
    private var borderWidth: Int = 0

    constructor() {
        this.borderColor = Color.TRANSPARENT
        this.borderWidth = 0
    }

    constructor(@ColorInt borderColor: Int, borderWidth: Int) {
        this.borderColor = borderColor
        this.borderWidth = borderWidth
    }

    override fun transform(source: Bitmap?): Bitmap? {
        if (source == null)
            return null

        val size = min(source.width, source.height)

        val width = (source.width - size) / 2
        val height = (source.height - size) / 2

        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888) ?: return source

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        if (width != 0 || height != 0) {
            val matrix = Matrix()
            matrix.setTranslate((-width).toFloat(), (-height).toFloat())
            shader.setLocalMatrix(matrix)
        }
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2F

        val paintBg = Paint()
        paintBg.color = borderColor
        paintBg.isAntiAlias = true

        canvas.drawCircle(r, r, r, paintBg)
        canvas.drawCircle(r, r, r - borderWidth, paint)
        source.recycle()

        return bitmap
    }

    override fun key(): String {
        return "circle borderColor=$borderColor, borderWidth$borderWidth"
    }
}