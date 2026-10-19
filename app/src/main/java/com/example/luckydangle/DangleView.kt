package com.example.luckydangle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import kotlin.math.cos
import kotlin.math.sin

class DangleView(context: Context) : View(context) {
    private val ropePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(190, 166, 119)
        strokeWidth = 4f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val goldPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(212, 175, 55)
        style = Paint.Style.FILL
    }
    private val darkPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(105, 55, 18)
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }
    private val shinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(255, 232, 150)
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    private var angleDegrees = -12f
    private var animator: ValueAnimator? = null

    init {
        // The overlay starts at the top-center of the screen. Adjust this point
        // if the phone's punch-hole camera is offset from the center.
        animator = ValueAnimator.ofFloat(-12f, 12f).apply {
            duration = 1500
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                angleDegrees = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val pivotX = width / 2f
        val pivotY = 0f
        val ropeLength = 225f
        val radians = Math.toRadians(angleDegrees.toDouble())
        val endX = pivotX + (sin(radians) * ropeLength).toFloat()
        val endY = pivotY + (cos(radians) * ropeLength).toFloat()

        canvas.drawLine(pivotX, pivotY, endX, endY, ropePaint)
        canvas.drawCircle(endX, endY + 7f, 8f, goldPaint)

        // Gold pendant body
        canvas.drawOval(endX - 22f, endY + 15f, endX + 22f, endY + 66f, goldPaint)
        canvas.drawOval(endX - 22f, endY + 15f, endX + 22f, endY + 66f, darkPaint)
        canvas.drawCircle(endX, endY + 39f, 11f, shinePaint)

        val shine = Path().apply {
            moveTo(endX - 5f, endY + 39f)
            lineTo(endX, endY + 31f)
            lineTo(endX + 5f, endY + 39f)
            lineTo(endX, endY + 47f)
            close()
        }
        canvas.drawPath(shine, shinePaint)
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        animator = null
        super.onDetachedFromWindow()
    }
}
