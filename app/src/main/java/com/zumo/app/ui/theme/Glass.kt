package com.zumo.app.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    glowColor: Color = ZumoAccents.default.glow,
    cornerRadius: Dp = 16.dp,
    borderAlpha: Float = 0.08f,
    glassAlpha: Float = 0.6f,
    glowSpread: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .clip(shape)
            .background(ZumoDarkSurface.copy(alpha = glassAlpha))
            .border(width = 1.dp, color = Color.White.copy(alpha = borderAlpha), shape = shape)
            .drawBehind {
                drawRoundRect(
                    color = glowColor.copy(alpha = 0.08f),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    size = size.copy(
                        width = size.width + glowSpread.toPx() * 2,
                        height = size.height + glowSpread.toPx() * 2
                    ),
                    topLeft = Offset(-glowSpread.toPx(), -glowSpread.toPx())
                )
            }
    ) {
        content()
    }
}
