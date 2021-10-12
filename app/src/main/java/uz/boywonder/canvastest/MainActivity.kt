package uz.boywonder.canvastest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import uz.boywonder.canvastest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            generateBtn.setOnClickListener {
                grid1.drawGrid()
                grid2.drawGrid()
                durationSlider.isEnabled = true
                instText.visibility = View.GONE
            }

            firstChipGroup.setOnCheckedChangeListener { group, checkedId ->

                val currentChip = group.findViewById<Chip>(checkedId)
                currentChip.setOnClickListener {
                    grid1.eventAction(it.tag.toString().toInt())
                }
            }

            secChipGroup.setOnCheckedChangeListener { group, checkedId ->

                val currentChip = group.findViewById<Chip>(checkedId)
                currentChip.setOnClickListener {
                    grid2.eventAction(it.tag.toString().toInt())
                }
            }

            durationSlider.addOnChangeListener { _, value, _ ->
                grid1.animDuration = (value * 1000).toLong()
                grid2.animDuration = (value * 1000).toLong()
            }

            durationSlider.apply {
                value = (grid1.animDuration / 1000).toFloat()
                isEnabled = false
            }

            sizeBtn.setOnClickListener {
                ResolutionDialog(
                    this@MainActivity,
                    grid1.layoutParams.width,
                    grid1.layoutParams.height
                ).apply {
                    setButtonClick { width, height ->

                        grid1.layoutParams.width = width
                        grid1.layoutParams.height = height

                        grid2.layoutParams.width = width
                        grid2.layoutParams.height = height

                    }
                    show()

                    instText.apply {
                        text = "Press Generate Button Again After Resizing"
                        visibility = View.VISIBLE
                    }
                }
            }

        }


    }
}