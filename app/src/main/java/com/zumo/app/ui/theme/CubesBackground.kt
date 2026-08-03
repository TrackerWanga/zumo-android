package com.zumo.app.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.*
import kotlin.random.Random

data class FloatingOrb(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val radius: Float,
    val alpha: Float,
    val color: Color
)

@Composable
fun CubesBackground(
    modifier: Modifier = Modifier,
    backgroundColor: Color = ZumoBackgrounds.default.base,
    gridColor: Color = ZumoBackgrounds.default.gridLine,
    accentGlow: Color = ZumoAccents.default.glow,
    content: @Composable () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cubes")
    val driftAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(120000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "drift"
    )

    val orbs = remember {
        List(6) {
            FloatingOrb(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                vx = (Random.nextFloat() - 0.5f) * 0.0003f,
                vy = (Random.nextFloat() - 0.5f) * 0.0003f,
                radius = Random.nextFloat() * 80f + 40f,
                alpha = Random.nextFloat() * 0.08f + 0.04f,
                color = accentGlow
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(color = backgroundColor)
            
            val cubeSize = 48f
            val gap = 64f
            val cols = (size.width / gap).toInt() + 2
            val rows = (size.height / gap).toInt() + 2

            val rad = Math.toRadians(driftAngle.toDouble()).toFloat()
            val offsetX = sin(rad) * 12f
            val offsetY = cos(rad) * 8f

            orbs.forEach { orb ->
                orb.x = (orb.x + orb.vx).coerceIn(0f, 1f)
                orb.y = (orb.y + orb.vy).coerceIn(0f, 1f)
                if (orb.x <= 0f || orb.x >= 1f) orb.vx *= -1
                if (orb.y <= 0f || orb.y >= 1f) orb.vy *= -1

                drawCircle(
                    color = orb.color.copy(alpha = orb.alpha),
                    radius = orb.radius * 2,
                    center = Offset(orb.x * size.width, orb.y * size.height)
                )
            }

            for (row in -1..rows) {
                for (col in -1..cols) {
                    val cx = col * gap + offsetX + (row % 2) * gap / 2f
                    val cy = row * gap * 0.75f + offsetY
                    if (cx < -cubeSize || cx > size.width + cubeSize ||
                        cy < -cubeSize || cy > size.height + cubeSize) continue

                    val path = Path().apply {
                        val hw = cubeSize * 0.45f
                        val hh = cubeSize * 0.25f
                        moveTo(cx, cy - hh)
                        lineTo(cx + hw, cy)
                        lineTo(cx, cy + hh)
                        lineTo(cx - hw, cy)
                        close()
                    }
                    drawPath(path, color = gridColor, style = Stroke(width = 0.8f))
                }
            }
        }
        
        content()
    }
}
