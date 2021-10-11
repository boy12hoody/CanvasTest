package uz.boywonder.canvastest.util

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
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

    private var maxCanvasValue = 0

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

    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        maxCanvasValue = maxOf(width, height)

        if (toDrawGrid) {
            drawDefaultGrid(canvas)
        } else {

            when (chosenEventType) {
                0 -> eventCirce(canvas)
                1 -> eventFlood(canvas)
                2 -> eventSunrise(canvas)
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
        animator?.cancel()
        chosenEventType = eventType
    }

    private fun drawDefaultGrid(canvas: Canvas) {

        paint.apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }


        val grid = maxCanvasValue / 50

        rectFloatList.clear()

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

        toDrawGrid = false
        hasGenerated = true

    }

    private var animator: ValueAnimator? = null
    private var currentValue = 0
    var animDuration = 5000L

    private fun startAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofInt(0, maxCanvasValue).apply {
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

    private fun eventCirce(canvas: Canvas) {

        paint.color = Color.BLACK
        for (rect in rectFloatList) {
            canvas.drawRect(rect, paint)
        }

        paint.apply {
            color = Color.CYAN
            style = Paint.Style.FILL
        }

        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            currentValue.toFloat(),
            paint
        )


    }

    private fun eventSunrise(canvas: Canvas) {

        paint.color = Color.BLACK
        for (rect in rectFloatList) {
            canvas.drawRect(rect, paint)
        }

        val path = Path()

        path.moveTo(0f, height.toFloat() - (currentValue / 5))
        path.quadTo(
            (width / 2).toFloat(),
            height * 0.8f - (currentValue / 5),
            width.toFloat(),
            height.toFloat() - (currentValue / 5))

        paint.apply {
            color = Color.GREEN
            style = Paint.Style.FILL
        }
        canvas.drawPath(path, paint)

        canvas.drawRect(
            0f,
            (height - (currentValue / 5)).toFloat(),
            width.toFloat(),
            height.toFloat(),
            paint
        )
    }


    private fun eventFlood(canvas: Canvas) {

        paint.color = Color.BLACK
        for (rect in rectFloatList) {
            canvas.drawRect(rect, paint)
        }

        paint.apply {
            color = Color.BLUE
            style = Paint.Style.FILL
        }

        canvas.drawRect(
            0f,
            (height - currentValue).toFloat(),
            width.toFloat(),
            height.toFloat(),
            paint
        )


    }


}