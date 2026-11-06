package com.example.snowflakes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.sin
import kotlin.random.Random

class Snowflake(private var width: Int, private var height: Int) {

    private var baseX: Float = Random.nextFloat() * width
    var x: Float = baseX
    var y: Float = Random.nextFloat() * height
    var radius: Float = Random.nextFloat() * 12f + 6f
    private var baseSpeed: Float = Random.nextFloat() * 8f + 4f
    private var amplitude: Float = Random.nextFloat() * 25f + 10f
    private var phase: Float = Random.nextFloat() * 2f * Math.PI.toFloat()

    val color: Int = createRgbColor(
        Random.nextInt(190, 240),
        Random.nextInt(210, 255),
        Random.nextInt(230, 256)
    )

    private val paint = Paint().apply {
        color = this@Snowflake.color
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    fun updateDimensions(w: Int, h: Int) {
        width = w
        height = h
    }

    fun move() {
        if (height <= 0) return

        val speedFactor = (1.0f - (y / height.toFloat()) * 0.75f).coerceIn(0.25f, 1.0f)
        y += baseSpeed * speedFactor

        phase += 0.03f
        x = baseX + sin((phase + y * 0.015f).toDouble()).toFloat() * amplitude

        if (y > height + radius) {
            reset()
        }
    }

    private fun reset() {
        y = -radius
        baseX = Random.nextFloat() * width
        x = baseX
        radius = Random.nextFloat() * 12f + 6f
        baseSpeed = Random.nextFloat() * 8f + 4f
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
