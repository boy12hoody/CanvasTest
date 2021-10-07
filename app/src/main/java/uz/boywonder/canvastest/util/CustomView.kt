package uz.boywonder.canvastest.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import uz.boywonder.canvastest.R
import kotlin.random.Random

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    var isGenerated = false
        set(state) {
            field = state
            invalidate()
        }

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {

        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomView,
            0, 0
        )

        isGenerated = typedArray.getBoolean(R.styleable.CustomView_isGenerated, false)
        typedArray.recycle()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (isGenerated) {
            true -> {
                drawGridForNoReason(canvas)
            }
            false -> {

            }
        }

    }

    private fun drawGridForNoReason(canvas: Canvas) {

        paint.apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }

        val maxCanvasValue = maxOf(canvas.width, canvas.height)

        val grid = maxCanvasValue / 50

        for (i in 0..maxCanvasValue step grid) {

            for (j in 0..maxCanvasValue step grid) {

                if (Random.nextInt(2) == 0) {
                    canvas.drawRect(
                        j.toFloat(),
                        i.toFloat(),
                        (j + grid).toFloat(),
                        (i + grid).toFloat(),
                        paint
                    )

                }
            }
        }

    }

}