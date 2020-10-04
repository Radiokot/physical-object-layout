package ua.com.radiokot.physicalitemlayoutsample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_scale_by_width.*
import ua.com.radiokot.physicalitemlayout.PhysicalItemLayout
import ua.com.radiokot.physicalitemlayoutsample.util.dp

class ScaleByWidthActivity : AppCompatActivity(R.layout.activity_scale_by_width) {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSamples()
    }

    private fun initSamples() {
        fun addSpace() =
                root_layout.addView(Space(this), ViewGroup.LayoutParams(0, 16.dp))

        val weightSum = 8
        val layoutInflater = LayoutInflater.from(this)

        fun addSample(weight: Int) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
            }

            val physicalItemLayout = PhysicalItemLayout(this).apply {
                scaleBy = PhysicalItemLayout.ScaleDimension.WIDTH
                layoutInflater.inflate(R.layout.layout_credit_card, this, true)
            }

            row.addView(physicalItemLayout, LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, weight.toFloat()
            ))
            row.addView(Space(this), LinearLayout.LayoutParams(
                    0, 0, (weightSum - weight).toFloat()
            ))

            root_layout.addView(row)
        }

        for (weight in 1..weightSum) {
            addSpace()
            addSample(weight)
        }

        addSpace()
    }
}