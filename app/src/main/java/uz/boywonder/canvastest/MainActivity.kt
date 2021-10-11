package uz.boywonder.canvastest

import android.os.Bundle
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

        }


    }
}