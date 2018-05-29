package com.xpanxion.architecture

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class AvailabilityGraph(context: Context, attributes: AttributeSet) : View(context, attributes) {
    private val paint: Paint = Paint()
    var availability: List<Float> = listOf()
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.style = Paint.Style.STROKE
        paint.color = 0xFF33B5E5.toInt()
        paint.strokeWidth = 4F
        paint.isAntiAlias = true
        paint.setShadowLayer(4F, 2F, 2F, 0x80000000.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        val max = availability.max()
        max?.let {
            val path = Path()
            availability.forEachIndexed { index, point ->
                if (index == 0) {
                    path.moveTo(0f, getScaledY(point, max))
                    path.lineTo(
                            getScaledX(index + 0.5f, availability.size),
                            getScaledY(point, max)
                    )
                } else {
                    path.lineTo(
                            getScaledX(index + 0.5f, availability.size),
                            getScaledY(point, max)
                    )
                }
            }
            path.lineTo(
                    getScaledX(availability.size.toFloat(), availability.size),
                    getScaledY(availability.last(), max)
            )
            canvas.drawPath(path, paint)
        }
    }


    private fun getScaledY(value: Float, max: Float): Float {
        var scaledValue: Float = value
        val graphHeight: Float = height.toFloat() - paddingTop.toFloat() - paddingBottom.toFloat()
        scaledValue = (scaledValue / max) * graphHeight
        scaledValue = graphHeight - scaledValue
        scaledValue += paddingTop.toFloat()
        return scaledValue
    }

    private fun getScaledX(value: Float, max: Int): Float {
        var scaledValue: Float = value
        val graphWidth: Float = width.toFloat() - paddingLeft.toFloat() - paddingRight.toFloat()
        scaledValue = (scaledValue / max) * graphWidth
        scaledValue += paddingLeft.toFloat()
        return scaledValue
    }
}