package ua.com.radiokot.physicalobjectlayout.scalingstrategy

import android.util.TypedValue
import android.view.View
import android.widget.TextView

object TextViewScalingStrategy: ViewScalingStrategy {
    override fun scaleView(view: View, scaleFactor: Double) {
        if (view !is TextView) {
            return
        }

        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.textSize * scaleFactor.toFloat())
    }
}