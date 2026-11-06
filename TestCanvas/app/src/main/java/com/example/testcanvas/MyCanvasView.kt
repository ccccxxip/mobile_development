package com.example.testcanvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class MyCanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 48f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val viewWidth = right - left
        val viewHeight = bottom - top
        Log.d("mytag", "onLayout: width = $viewWidth px, height = $viewHeight px (changed=$changed)")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height

        Log.d("mytag", "onDraw: canvas.width = $canvasWidth px, canvas.height = $canvasHeight px")

        val cx = canvasWidth / 2f
        val cy = canvasHeight / 2f
        val radius = minOf(canvasWidth, canvasHeight) / 3f

        canvas.drawCircle(cx, cy, radius, paint)
        canvas.drawText("Canvas View", cx, cy + 16f, textPaint)
    }
}
