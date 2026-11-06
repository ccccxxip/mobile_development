package com.example.snowflakes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.AsyncTask
import android.util.AttributeSet
import android.view.View

class SnowflakesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val snowflakes = mutableListOf<Snowflake>()
    private var snowTask: SnowTask? = null
    private val count = 120

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        snowflakes.clear()
        for (i in 0 until count) {
            snowflakes.add(Snowflake(w, h))
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.parseColor("#0B1D3A"))
        for (snowflake in snowflakes) {
            snowflake.draw(canvas)
        }
    }

    fun updateSnowflakes() {
        for (snowflake in snowflakes) {
            snowflake.move()
        }
    }

    fun startAnimation() {
        if (snowTask == null || snowTask?.status == AsyncTask.Status.FINISHED) {
            snowTask = SnowTask(this)
            snowTask?.execute()
        }
    }

    fun stopAnimation() {
        snowTask?.cancel(true)
        snowTask = null
    }

    @Suppress("DEPRECATION")
    private class SnowTask(private val view: SnowflakesView) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            while (!isCancelled) {
                view.updateSnowflakes()
                view.postInvalidate()
                try {
                    Thread.sleep(20) // ~50 FPS
                } catch (e: InterruptedException) {
                    break
                }
            }
            return null
        }
    }
}
