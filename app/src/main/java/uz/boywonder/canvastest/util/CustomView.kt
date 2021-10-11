package uz.boywonder.canvastest.util

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.random.Random

/**
 * @author telegram: @BoyWonder
 * @mail boy12hoody@gmail.com
 * @data 5/10/21
 * @project CustomView Canvas Task
 */

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    private var chosenEventType = 0

    /* Restricts onDraw from generating random white-black area again and again */
    private var toDrawGrid = false

    /* Avoids initial blank area being clicked */
    private var hasGenerated = false

    /* List to hold random generated area rectangles to use with animations */
    private val rectFloatList = mutableListOf<RectF>()

    private val sizeRect = RectF(0f, 0f, 0f, 0f)

    private var startX = 0f
    private var startY = 0f
    private var stopX = 0f
    private var stopY = 0f

    /* Exposed function to draw/generate white-black area */
    fun drawGrid() {
        toDrawGrid = true
        animator?.cancel()
        invalidate()

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sizeRect.set(0f, 0f, w.toFloat(), h.toFloat())
        Log.d("CANVAS", "onSizeChanged called")

        setSizeRect()
    }

    private fun setSizeRect() {
        startX = 0f
        startY = sizeRect.height()
        stopX = sizeRect.width()
        stopY = sizeRect.height()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (toDrawGrid) {
            drawGridForNoReason(canvas)
        } else {

            Log.d(
                "CANVAS",
                "$height / $width / chosenEventType: $chosenEventType"
            )

            when (chosenEventType) {
                0 -> {
                    drawRain(canvas)
                }
                1 -> {
                    drawFlood(canvas)
                }
                2 -> {
                    drawWind(canvas)
                }
            }
        }

    }

    init {
        setOnClickListener {
            if (hasGenerated) {

                startAnimation()

            }
        }
    }

    fun eventAction(eventType: Int) {

        chosenEventType = eventType
        Log.d(
            "CANVAS",
            "$height / $width / eventType: $eventType"
        )

    }

    private fun drawGridForNoReason(canvas: Canvas) {

        paint.apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }

        val maxCanvasValue = maxOf(canvas.width, canvas.height)
        val grid = maxCanvasValue / 50

        rectFloatList.clear()
        setSizeRect()

        for (i in 0..maxCanvasValue step grid) {

            for (j in 0..maxCanvasValue step grid) {

                if (Random.nextInt(2) == 0) {

                    /* Creating and Adding Rectangles to use it later
                    * and Clearing the list not add previous ones too */

                    val rectF = RectF(
                        j.toFloat(),
                        i.toFloat(),
                        (j + grid).toFloat(),
                        (i + grid).toFloat()
                    )

                    rectFloatList.add(rectF)

                    canvas.drawRect(
                        rectF,
                        paint
                    )
                }
            }
        }

        /* */
        toDrawGrid = false
        hasGenerated = true

    }

    private var animator: ValueAnimator? = null
    private var currentValue = 0
    var animDuration = 5000L

    private fun startAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofInt(0, 100).apply {
            duration = animDuration
            interpolator = LinearInterpolator()
            addUpdateListener { valueAnimator ->
                currentValue = valueAnimator.animatedValue as Int
                Log.d("ANIMATOR", "currentValue: $currentValue")
                invalidate()
            }
        }
        animator?.start()
    }

    private fun drawRain(canvas: Canvas) {

    }

    private fun drawWind(canvas: Canvas) {

    }


    private fun drawFlood(canvas: Canvas) {

        Log.d("CANVAS", "drawFlood called")

        paint.color = Color.BLACK
        for (rect in rectFloatList) {
            canvas.drawRect(rect, paint)
        }

        paint.apply {
            color = Color.BLUE
            style = Paint.Style.FILL
        }

        Log.d("CANVAS", "h: ${sizeRect.height()} / w: ${sizeRect.width()}")

        canvas.drawRect(startX, startY, stopX, stopY, paint)

        startY -= 5

        Log.d("CANVAS", "drawFlood startY: $startY")

    }


}