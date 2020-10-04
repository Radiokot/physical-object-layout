package ua.com.radiokot.physicalobjectlayoutsample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_scale_by_width.*
import kotlinx.android.synthetic.main.layout_credit_card.view.*
import ua.com.radiokot.physicalobjectlayout.PhysicalObjectLayout
import ua.com.radiokot.physicalobjectlayoutsample.util.dp
import kotlin.random.Random

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

            val physicalObjectLayout = PhysicalObjectLayout(this).apply {
                scaleBy = PhysicalObjectLayout.ScaleDimension.WIDTH

                val creditCard =
                        layoutInflater.inflate(R.layout.layout_credit_card, this, false)
                creditCard.setOnClickListener {
                    for (i in 0..3) {
                        (creditCard.card_number_groups_layout[i] as TextView).text =
                                Random.nextInt(1111, 9999).toString()
                    }
                }

                addView(creditCard)
            }

            row.addView(physicalObjectLayout, LinearLayout.LayoutParams(
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