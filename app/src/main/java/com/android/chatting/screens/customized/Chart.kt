package com.android.chatting.screens.customized

import android.widget.Button
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun LineChartDemo() {
    var isDayView by remember { mutableStateOf(true) }
    val points = if (isDayView) dayData else weekData

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { isDayView = true }) { Text("Day Chart") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                isDayView = false
            }) { Text("Week Chart") }
        }

        Spacer(Modifier.height(16.dp))

        LineChart(
            points = points,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        )
    }
}

@Composable
fun LineChart(points: List<Float>, modifier: Modifier = Modifier) {
    var selectedIndex by remember { mutableStateOf(-1) }

    LaunchedEffect(points) {
        selectedIndex = -1
    }

    Canvas(modifier =
        modifier
            .pointerInput(points) {
                detectTapGestures { offset ->
                    // 计算点击位置对应点索引
                    val index = ((offset.x / size.width) * (points.size - 1)).toInt()
                    selectedIndex = index.coerceIn(0, points.lastIndex)
                }
            }
    ) {
        // X 轴
        drawLine(
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            color = Color.Gray.copy(alpha = 0.8f),
            strokeWidth = 2f
        )

        // Y 轴
        drawLine(
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            color = Color.Gray.copy(alpha = 0.8f),
            strokeWidth = 2f
        )

        if (points.isEmpty()) return@Canvas

        val maxY = points.maxOrNull() ?: 0f
        val minY = points.minOrNull() ?: 0f
        val stepX = size.width / (points.size - 1)
        val yScale = size.height / (maxY - minY)

        // 把 Float 转成 Offset 点坐标
        val pointOffsets = points.mapIndexed { i, y ->
            Offset(
                x = i * stepX,
                y = size.height - (y - minY) * yScale
            )
        }

        // 使用 cubic 贝塞尔曲线平滑折线
        val path = Path().apply { smoothLine(pointOffsets) }

        drawPath(
            path,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF007AFF), Color(0xFF00C6FF))
            ),
            style = Stroke(width = 5f, cap = StrokeCap.Round)
        )

        pointOffsets.forEach {
            drawCircle(
                color = Color.White,
                radius = 5f,
                center = it
            )
        }

        // 绘制被选中的点
        if (selectedIndex >= 0) {
            val point = pointOffsets[selectedIndex]
            val value = points[selectedIndex]

            drawCircle(
                color = Color.Red,
                radius = 8f,
                center = point
            )
            // Tooltip
            val tooltipWidth = 80f
            val tooltipHeight = 40f
            val tooltipX = (point.x - tooltipWidth / 2).coerceIn(0f, size.width - tooltipWidth)
            val tooltipY = (point.y - tooltipHeight - 12f).coerceAtLeast(0f)

            drawRoundRect(
                color = Color(0xFF202020),
                topLeft = Offset(tooltipX, tooltipY),
                size = androidx.compose.ui.geometry.Size(tooltipWidth, tooltipHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )

            drawContext.canvas.nativeCanvas.drawText(
                "Value: %.2f".format(value),
                tooltipX + 10f,
                tooltipY + 25f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 28f
                    isFakeBoldText = true
                }
            )
        }

    }
}

private fun Path.smoothLine(points: List<Offset>) {
    if (points.isEmpty()) return
    moveTo(points.first().x, points.first().y) // 起点
    for (i in 1 until points.size) {
        val prev = points[i - 1]
        val curr = points[i]
        val midX = (prev.x + curr.x) / 2 // 计算两点之间的中点
        // 使用三次贝塞尔曲线（Cubic Bezier）连接
        cubicTo(midX, prev.y, midX, curr.y, curr.x, curr.y)
    }
}

data class ChartPoint(val x: Float, val y: Float)

val dayData = listOf(
    3.2f, 3.5f, 3.3f, 3.8f, 4.1f, 4.4f, 4.2f, 4.6f, 4.8f, 4.7f,
    5.0f, 5.3f, 5.1f, 5.6f, 5.8f, 6.0f, 6.3f, 6.1f, 6.5f, 6.8f,
    6.4f, 6.9f, 7.2f, 7.5f, 7.1f, 7.3f, 7.0f, 7.6f, 7.9f, 8.2f
)

val weekData = listOf(
    2.5f, 2.7f, 2.8f, 3.0f, 3.2f, 3.1f, 3.4f, 3.8f, 3.6f, 3.9f,
    4.2f, 4.1f, 4.5f, 4.3f, 4.7f, 4.9f, 4.6f, 4.8f, 5.1f, 5.3f,
    5.0f, 5.5f, 5.7f, 5.6f, 5.8f, 6.1f, 5.9f, 6.3f, 6.5f, 6.2f
)