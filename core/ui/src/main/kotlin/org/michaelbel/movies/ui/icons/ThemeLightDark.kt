package org.michaelbel.movies.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Convert SVG to Compose using https://composables.com/svgtocompose
 * SVG Icon: https://pictogrammers.com/library/mdi/icon/theme-light-dark
 */
val MoviesIcons.ThemeLightDark: ImageVector
	get() {
		if (_themeLightDark != null) {
			return _themeLightDark!!
		}
		_themeLightDark = ImageVector.Builder(
			name = "ThemeLightDark",
			defaultWidth = 24.dp,
			defaultHeight = 24.dp,
			viewportWidth = 24f,
			viewportHeight = 24f
		).apply {
			path(
				fill = SolidColor(Color.Black),
				fillAlpha = 1.0f,
				stroke = null,
				strokeAlpha = 1.0f,
				strokeLineWidth = 1.0f,
				strokeLineCap = StrokeCap.Butt,
				strokeLineJoin = StrokeJoin.Miter,
				strokeLineMiter = 1.0f,
				pathFillType = PathFillType.NonZero
			) {
				moveTo(7.5f, 2f)
				curveTo(5.71f, 3.15f, 4.5f, 5.18f, 4.5f, 7.5f)
				curveTo(4.5f, 9.82f, 5.71f, 11.85f, 7.53f, 13f)
				curveTo(4.46f, 13f, 2f, 10.54f, 2f, 7.5f)
				arcTo(5.5f, 5.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 7.5f, 2f)
				moveTo(19.07f, 3.5f)
				lineTo(20.5f, 4.93f)
				lineTo(4.93f, 20.5f)
				lineTo(3.5f, 19.07f)
				lineTo(19.07f, 3.5f)
				moveTo(12.89f, 5.93f)
				lineTo(11.41f, 5f)
				lineTo(9.97f, 6f)
				lineTo(10.39f, 4.3f)
				lineTo(9f, 3.24f)
				lineTo(10.75f, 3.12f)
				lineTo(11.33f, 1.47f)
				lineTo(12f, 3.1f)
				lineTo(13.73f, 3.13f)
				lineTo(12.38f, 4.26f)
				lineTo(12.89f, 5.93f)
				moveTo(9.59f, 9.54f)
				lineTo(8.43f, 8.81f)
				lineTo(7.31f, 9.59f)
				lineTo(7.65f, 8.27f)
				lineTo(6.56f, 7.44f)
				lineTo(7.92f, 7.35f)
				lineTo(8.37f, 6.06f)
				lineTo(8.88f, 7.33f)
				lineTo(10.24f, 7.36f)
				lineTo(9.19f, 8.23f)
				lineTo(9.59f, 9.54f)
				moveTo(19f, 13.5f)
				arcTo(5.5f, 5.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 13.5f, 19f)
				curveTo(12.28f, 19f, 11.15f, 18.6f, 10.24f, 17.93f)
				lineTo(17.93f, 10.24f)
				curveTo(18.6f, 11.15f, 19f, 12.28f, 19f, 13.5f)
				moveTo(14.6f, 20.08f)
				lineTo(17.37f, 18.93f)
				lineTo(17.13f, 22.28f)
				lineTo(14.6f, 20.08f)
				moveTo(18.93f, 17.38f)
				lineTo(20.08f, 14.61f)
				lineTo(22.28f, 17.15f)
				lineTo(18.93f, 17.38f)
				moveTo(20.08f, 12.42f)
				lineTo(18.94f, 9.64f)
				lineTo(22.28f, 9.88f)
				lineTo(20.08f, 12.42f)
				moveTo(9.63f, 18.93f)
				lineTo(12.4f, 20.08f)
				lineTo(9.87f, 22.27f)
				lineTo(9.63f, 18.93f)
				close()
			}
		}.build()
		return _themeLightDark!!
	}

private var _themeLightDark: ImageVector? = null