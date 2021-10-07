package uz.boywonder.canvastest.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawGridForNoReason(canvas)
    }

    private fun drawGridForNoReason(canvas: Canvas) {

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
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