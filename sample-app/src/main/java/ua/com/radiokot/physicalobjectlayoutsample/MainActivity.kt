package ua.com.radiokot.physicalobjectlayoutsample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButtons()
    }

    private fun initButtons() {
        scale_by_width_button.setOnClickListener {
            startActivity(Intent(this, ScaleByWidthActivity::class.java))
        }

        scale_by_height_button.setOnClickListener {
        }
    }
}