package ua.com.radiokot.physicalobjectlayoutsample

import android.content.res.Resources
import android.util.Log
import android.view.View
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import ua.com.radiokot.physicalobjectlayout.scalingstrategy.ViewScalingStrategy
import ua.com.radiokot.physicalobjectlayoutsample.util.dp
import kotlin.math.roundToInt

object CircularProgressBarScalingStrategy : ViewScalingStrategy {
    override fun scaleView(view: View, scaleFactor: Double) {
        if (view !is CircularProgressBar) {
            return
        }

        view.apply {
            val floatScaleFactor = scaleFactor.toFloat()

            progressBarWidth = progressBarWidth.pxAsDp() * floatScaleFactor
            backgroundProgressBarWidth = backgroundProgressBarWidth.pxAsDp() * floatScaleFactor
        }
    }

    private fun Float.pxAsDp(): Float {
        return this / Resources.getSystem().displayMetrics.density
    }
}