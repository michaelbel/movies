package org.michaelbel.movies.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MoviesIcons.Telegram: ImageVector
    get() {
        if (_telegram != null) {
            return _telegram!!
        }
        
        _telegram = ImageVector.Builder(
            name = "tg",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(1.64987f, 10.6099f)
                curveTo(8.09231f, 7.78741f, 12.3883f, 5.92667f, 14.5378f, 5.02765f)
                curveTo(20.675f, 2.46077f, 21.9503f, 2.01488f, 22.7815f, 2.00016f)
                curveTo(22.9643f, 1.99692f, 23.3731f, 2.04248f, 23.6379f, 2.25852f)
                curveTo(23.8614f, 2.44094f, 23.9229f, 2.68737f, 23.9524f, 2.86033f)
                curveTo(23.9818f, 3.03328f, 24.0185f, 3.42728f, 23.9893f, 3.73514f)
                curveTo(23.6568f, 7.249f, 22.2177f, 15.7762f, 21.4856f, 19.7118f)
                curveTo(21.1758f, 21.3771f, 20.5658f, 21.9354f, 19.9753f, 21.9901f)
                curveTo(18.6919f, 22.1088f, 17.7174f, 21.1372f, 16.4744f, 20.3179f)
                curveTo(14.5294f, 19.0358f, 13.4305f, 18.2377f, 11.5425f, 16.9867f)
                curveTo(9.36064f, 15.5408f, 10.7751f, 14.7462f, 12.0185f, 13.4475f)
                curveTo(12.344f, 13.1076f, 17.9985f, 7.93584f, 18.1079f, 7.46668f)
                curveTo(18.1216f, 7.40801f, 18.1343f, 7.18929f, 18.0051f, 7.0738f)
                curveTo(17.8759f, 6.95831f, 17.6851f, 6.9978f, 17.5475f, 7.02921f)
                curveTo(17.3524f, 7.07373f, 14.2452f, 9.1389f, 8.22584f, 13.2247f)
                curveTo(7.34386f, 13.8337f, 6.545f, 14.1304f, 5.82924f, 14.1149f)
                curveTo(5.04018f, 14.0977f, 3.52233f, 13.6663f, 2.39397f, 13.2974f)
                curveTo(1.00999f, 12.8451f, -0.0899676f, 12.6059f, 0.00581422f, 11.8376f)
                curveTo(0.0557033f, 11.4374f, 0.603723f, 11.0282f, 1.64987f, 10.6099f)
                close()
            }
        }.build()
        
        return _telegram!!
    }

private var _telegram: ImageVector? = null
