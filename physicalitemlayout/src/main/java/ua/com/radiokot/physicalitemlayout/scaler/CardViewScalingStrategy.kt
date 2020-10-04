package ua.com.radiokot.physicalitemlayout.scaler

import android.view.View
import androidx.cardview.widget.CardView

object CardViewScalingStrategy : ViewScalingStrategy {
    override fun scaleView(view: View, scaleFactor: Double) {
        if (view !is CardView) {
            return
        }

        view.apply {
            val floatScaleFactor = scaleFactor.toFloat()

            radius *= floatScaleFactor
            cardElevation *= floatScaleFactor
            maxCardElevation *= floatScaleFactor
        }
    }
}