package ua.com.radiokot.physicalobjectlayoutsample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_scale_by_height.*
import ua.com.radiokot.physicalobjectlayout.PhysicalObjectLayout
import ua.com.radiokot.physicalobjectlayoutsample.util.dp

class ScaleByHeightActivity: AppCompatActivity(R.layout.activity_scale_by_height) {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSamples()
    }

    private fun initSamples() {
        fun addSpace() =
                root_layout.addView(Space(this), ViewGroup.LayoutParams(16.dp, 0))

        val weightSum = 8
        val layoutInflater = LayoutInflater.from(this)

        fun addSample(weight: Int) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
            }

            val physicalObjectLayout = PhysicalObjectLayout(this).apply {
                scaleBy = PhysicalObjectLayout.ScaleDimension.HEIGHT
                layoutInflater.inflate(R.layout.layout_badge_card, this, true)
            }

            row.addView(physicalObjectLayout, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0, weight.toFloat()
            ))
            row.addView(Space(this), LinearLayout.LayoutParams(
                    0, 0, (weightSum - weight).toFloat()
            ))

            root_layout.addView(row, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT))
        }

        for (weight in 1..weightSum) {
            addSpace()
            addSample(weight)
        }

        addSpace()
    }
}