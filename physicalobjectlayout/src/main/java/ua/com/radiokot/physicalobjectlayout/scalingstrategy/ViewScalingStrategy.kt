package ua.com.radiokot.physicalobjectlayout.scalingstrategy

import android.view.View

/**
 * A strategy of scaling particular view
 */
interface ViewScalingStrategy {
    fun scaleView(view: View, scaleFactor: Double)
}