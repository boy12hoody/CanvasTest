package uz.boywonder.canvastest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                grid1.isGenerated = true
                grid2.isGenerated = true
            }

            firstChipGroup.setOnCheckedChangeListener { group, checkedId ->
                Log.d("CHECKED", checkedId.toString())

            }

            secChipGroup.setOnCheckedChangeListener { group, checkedId ->
                Log.d("CHECKED", checkedId.toString())

            }

        }
    }
}