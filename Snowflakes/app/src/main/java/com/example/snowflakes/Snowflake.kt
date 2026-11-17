package com.example.snowflakes

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.sin
import kotlin.random.Random

class Snowflake(private var width: Int, private var height: Int) {

    private var baseX: Float = Random.nextFloat() * width
    var x: Float = baseX
    var y: Float = Random.nextFloat() * height
    var radius: Float = (Random.nextFloat() * 12f) + 6f
    private var baseSpeed: Float = (Random.nextFloat() * 10f) + 5f
    private var amplitude: Float = (Random.nextFloat() * 25f) + 10f
    private var phase: Float = Random.nextFloat() * 2f * Math.PI.toFloat()

    val color: Int = createRgbColor(
        Random.nextInt(190, 240),
        Random.nextInt(210, 255),
        Random.nextInt(230, 256),
    )

    private val paint = Paint().apply {
        color = this@Snowflake.color
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    fun move() {
        if (height <= 0) return

        // Сгущение книзу: скорость существенно падает к низу экрана,
        // из-за чего снежинки накапливаются и создают видимую густоту внизу.
        val progress = (y / height.toFloat()).coerceIn(0f, 1f)
        val speedFactor = (1.0f - progress * 0.88f) * (1.0f - progress * 0.88f)
        val effectiveSpeed = (baseSpeed * speedFactor).coerceAtLeast(0.6f)

        y += effectiveSpeed

        phase += 0.035f
        x = baseX + sin((phase + y * 0.012f).toDouble()).toFloat() * amplitude

        if (y > height + radius) {
            reset()
        }
    }

    private fun reset() {
        y = -radius
        baseX = Random.nextFloat() * width
        x = baseX
        radius = (Random.nextFloat() * 12f) + 6f
        baseSpeed = (Random.nextFloat() * 10f) + 5f
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, radius, paint)
    }

    companion object {
        fun createRgbColor(red: Int, green: Int, blue: Int): Int {
            return (0xFF shl 24) or (red shl 16) or (green shl 8) or blue
        }
    }
}
