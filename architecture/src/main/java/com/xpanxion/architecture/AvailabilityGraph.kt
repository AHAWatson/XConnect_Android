package com.xpanxion.architecture

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

private const val SMOOTHNESS = 0.05f
private const val CHART_COLOR = 0xFF0099CC

class AvailabilityGraph(context: Context, attributes: AttributeSet) : View(context, attributes) {
    private val path = Path()
    private val paint = Paint()
    private var dst: Rect = Rect()
    private val fillPaint = Paint()
    private val backgroundBitmap: Bitmap
    private val backgroundPaint = Paint()
    private var points = ArrayList<PointF>()
    var availability: List<Float> = listOf()
        set(value) {
            field = value
            updatePoints()
            invalidate()
        }

    init {
        paint.style = Paint.Style.STROKE
        paint.color = 0xFF33B5E5.toInt()
        paint.strokeWidth = 4F
        paint.isAntiAlias = true
        paint.setShadowLayer(4F, 2F, 2F, 0x80000000.toInt())

        fillPaint.style = Paint.Style.FILL
        fillPaint.color = (CHART_COLOR and 0xFFFFFF or 0x10000000).toInt()

        backgroundPaint.style = Paint.Style.FILL_AND_STROKE
        backgroundBitmap = Bitmap.createBitmap(6, 1, Bitmap.Config.ARGB_8888)
        val c = Canvas(backgroundBitmap)

        backgroundPaint.color = Color.parseColor("#E9E9E9")
        c.drawRect(0f, 0f, 1f, 1f, backgroundPaint)

        backgroundPaint.color = Color.parseColor("#D9D9D9")
        c.drawRect(1f, 0f, 2f, 1f, backgroundPaint)

        backgroundPaint.color = Color.parseColor("#E9E9E9")
        c.drawRect(2f, 0f, 3f, 1f, backgroundPaint)

        backgroundPaint.color = Color.parseColor("#D9D9D9")
        c.drawRect(3f, 0f, 4f, 1f, backgroundPaint)

        backgroundPaint.color = Color.parseColor("#E9E9E9")
        c.drawRect(4f, 0f, 5f, 1f, backgroundPaint)

        backgroundPaint.color = Color.parseColor("#D9D9D9")
        c.drawRect(5f, 0f, 6f, 1f, backgroundPaint)
    }

    private fun updatePoints() {
        points = ArrayList(availability.size)
        availability.forEachIndexed { index, value ->
            points.add(PointF(index + 0.1f, value))
            points.add(PointF(index + 0.3f, value))
            points.add(PointF(index + 0.5f, value))
            points.add(PointF(index + 0.7f, value))
            points.add(PointF(index + 0.9f, value))
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (points.size > 0) {
            path.reset()
            var lX = 0f
            var lY = 0f
            path.moveTo(getScaledX(points[0].x, availability.size), getScaledY(points[0].y, 100f))
            for (i in 1 until points.size) {
                val p = points[i]

                // first control point
                val p0 = points[i - 1]
                val x1 = p0.x + lX
                val y1 = p0.y + lY

                // second control point
                val p1 = points[if (i + 1 < points.size) i + 1 else i]
                lX = (p1.x - p0.x) / 2 * SMOOTHNESS
                lY = (p1.y - p0.y) / 2 * SMOOTHNESS
                val x2 = p.x - lX
                val y2 = p.y - lY

                path.cubicTo(
                        getScaledX(x1, availability.size),
                        getScaledY(y1, 100f),
                        getScaledX(x2, availability.size),
                        getScaledY(y2, 100f),
                        getScaledX(p.x, availability.size),
                        getScaledY(p.y, 100f)
                )
            }
            canvas.drawBitmap(backgroundBitmap, null, dst, backgroundPaint)
            canvas.drawPath(path, paint)

            path.lineTo(getScaledX(points[points.size-1].x, availability.size), height.toFloat())
            path.lineTo(getScaledX(points[0].x, availability.size), height.toFloat())
            path.close()
            canvas.drawPath(path, fillPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        dst = Rect(0, 0, w, h)
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