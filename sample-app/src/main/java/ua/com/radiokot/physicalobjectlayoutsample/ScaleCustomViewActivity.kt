package ua.com.radiokot.physicalobjectlayoutsample

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_scale_custom_view.*
import ua.com.radiokot.physicalobjectlayout.PhysicalObjectLayout

class ScaleCustomViewActivity : AppCompatActivity(R.layout.activity_scale_custom_view) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSamples()
    }

    private fun initSamples() {
        val weightSum = 3

        fun addSample(weight: Int) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
            }

            val physicalObjectLayout = PhysicalObjectLayout(this).apply {
                scaleBy = PhysicalObjectLayout.ScaleDimension.WIDTH

                addScalingStrategies(CircularProgressBarScalingStrategy)

                LayoutInflater.from(context)
                        .inflate(R.layout.layout_with_custom_view, this, true)
            }

            row.addView(physicalObjectLayout, LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, weight.toFloat()
            ))

            row.addView(Space(this), LinearLayout.LayoutParams(
                    0, 0, (weightSum - weight).toFloat()
            ))

            root_layout.addView(row, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ))
        }

        for (weight in (1..weightSum)) {
            addSample(weight)
        }
    }
}