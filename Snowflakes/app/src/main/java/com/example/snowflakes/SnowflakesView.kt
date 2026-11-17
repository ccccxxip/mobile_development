package com.example.snowflakes

import android.content.Context
import android.graphics.Canvas
import android.os.AsyncTask
import android.util.AttributeSet
import android.view.View
import java.lang.ref.WeakReference

@Suppress("DEPRECATION")
class SnowflakesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val snowflakes = mutableListOf<Snowflake>()
    private var snowTask: SnowTask? = null
    private val count = 180
    private val backgroundColor = 0xFF0B1D3A.toInt()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        snowflakes.clear()
        repeat(count) {
            snowflakes.add(Snowflake(w, h))
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(backgroundColor)
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
        if ((snowTask == null) || (snowTask?.status == AsyncTask.Status.FINISHED)) {
            snowTask = SnowTask(this)
            snowTask?.execute()
        }
    }

    fun stopAnimation() {
        snowTask?.cancel(true)
        snowTask = null
    }

    private class SnowTask(view: SnowflakesView) : AsyncTask<Void, Void, Void>() {
        private val viewRef = WeakReference(view)

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Void? {
            while (!isCancelled) {
                val v = viewRef.get() ?: break
                v.updateSnowflakes()
                v.postInvalidate()
                try {
                    Thread.sleep(20)
                } catch (_: InterruptedException) {
                    break
                }
            }
            return null
        }
    }
}
