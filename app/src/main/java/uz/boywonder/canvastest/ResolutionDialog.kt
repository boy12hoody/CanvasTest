package uz.boywonder.canvastest

import android.app.AlertDialog
import android.content.Context
import uz.boywonder.canvastest.databinding.DialogResolutionBinding

class ResolutionDialog(context: Context, width: Int, height: Int) : AlertDialog(context) {
    private val binding = DialogResolutionBinding.inflate(layoutInflater)

    private var buttonClickListener: ((width: Int, height: Int) -> Unit)? = null
    fun setButtonClick(function: (width: Int, height: Int) -> Unit) {
        buttonClickListener = function
    }

    init {


        binding.apply {

            widthText.editText?.setText(width.toString())
            heightText.editText?.setText(height.toString())

            applyBtn.setOnClickListener {
                if (widthText.editText!!.text.isNotBlank() && heightText.editText!!.text.isNotBlank()) {
                    buttonClickListener?.invoke(
                        widthText.editText!!.text.toString().toInt(),
                        heightText.editText!!.text.toString().toInt()
                    )
                    dismiss()
                }

            }
        }

        setView(binding.root)
    }


}