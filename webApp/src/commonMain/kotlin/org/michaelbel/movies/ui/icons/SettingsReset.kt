package org.michaelbel.movies.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

val MoviesIcons.SettingsReset: ImageVector
    get() {
        if (_settingsReset != null) {
            return _settingsReset!!
        }
        _settingsReset = ImageVector.Builder(
            name = "SettingsReset",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960F,
            viewportHeight = 960F
        ).apply {
            addPath(
                pathData = PathParser().parsePathString("M550,570h100q13,0 21.5,8.5T680,600q0,13 -8.5,21.5T650,630L550,630q-13,0 -21.5,-8.5T520,600q0,-13 8.5,-21.5T550,570ZM580,810v-20h-30q-13,0 -21.5,-8.5T520,760q0,-13 8.5,-21.5T550,730h30v-20q0,-13 8.5,-21.5T610,680q13,0 21.5,8.5T640,710v100q0,13 -8.5,21.5T610,840q-13,0 -21.5,-8.5T580,810ZM710,730h100q13,0 21.5,8.5T840,760q0,13 -8.5,21.5T810,790L710,790q-13,0 -21.5,-8.5T680,760q0,-13 8.5,-21.5T710,730ZM720,650v-100q0,-13 8.5,-21.5T750,520q13,0 21.5,8.5T780,550v20h30q13,0 21.5,8.5T840,600q0,13 -8.5,21.5T810,630h-30v20q0,13 -8.5,21.5T750,680q-13,0 -21.5,-8.5T720,650ZM480,200q-117,0 -198.5,81.5T200,480q0,72 32.5,132t87.5,98v-70q0,-17 11.5,-28.5T360,600q17,0 28.5,11.5T400,640v160q0,17 -11.5,28.5T360,840L200,840q-17,0 -28.5,-11.5T160,800q0,-17 11.5,-28.5T200,760h54q-62,-50 -98,-122.5T120,480q0,-75 28.5,-140.5t77,-114q48.5,-48.5 114,-77T480,120q113,0 203.5,63T814,345q6,16 0,31t-22,21q-16,6 -31.5,0T739,375q-31,-78 -100.5,-126.5T480,200Z").toNodes(),
                fill = SolidColor(Color.Black),
                fillAlpha = 1F,
                stroke = null,
                strokeAlpha = 1F,
                strokeLineWidth = 1F,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1F,
                pathFillType = PathFillType.NonZero
            )
        }.build()
        return _settingsReset!!
    }

private var _settingsReset: ImageVector? = null
