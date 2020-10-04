package ua.com.radiokot.physicalobjectlayout.scalingstrategy

import android.view.View
import android.view.ViewGroup
import kotlin.math.roundToInt

object GeneralViewScalingStrategy: ViewScalingStrategy {
    override fun scaleView(view: View, scaleFactor: Double) {
        scaleLayoutParams(view, scaleFactor)
        scalePadding(view, scaleFactor)
    }

    @JvmStatic
    fun scaleLayoutParams(view: View, scaleFactor: Double) {
        var changesOccurred = false
        view.layoutParams.apply {
            if (width > 0) {
                width = (width * scaleFactor).roundToInt()
                changesOccurred = true
            }
            if (height > 0) {
                height = (height * scaleFactor).roundToInt()
                changesOccurred = true
            }

            if (this is ViewGroup.MarginLayoutParams) {
                if (leftMargin != 0) {
                    leftMargin = (leftMargin * scaleFactor).roundToInt()
                    changesOccurred = true
                }
                if (rightMargin != 0) {
                    rightMargin = (rightMargin * scaleFactor).roundToInt()
                    changesOccurred = true
                }
                if (topMargin != 0) {
                    topMargin = (topMargin * scaleFactor).roundToInt()
                    changesOccurred = true
                }
                if (bottomMargin != 0) {
                    bottomMargin = (bottomMargin * scaleFactor).roundToInt()
                    changesOccurred = true
                }
            }
        }

        if (changesOccurred) {
            view.invalidate()
        }
    }

    @JvmStatic
    fun scalePadding(view: View, scaleFactor: Double) {
        view.setPadding(
                (view.paddingLeft * scaleFactor).roundToInt(),
                (view.paddingTop * scaleFactor).roundToInt(),
                (view.paddingRight * scaleFactor).roundToInt(),
                (view.paddingBottom * scaleFactor).roundToInt()
        )
    }
}