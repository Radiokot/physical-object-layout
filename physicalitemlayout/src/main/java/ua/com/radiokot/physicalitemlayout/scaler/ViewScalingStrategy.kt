package ua.com.radiokot.physicalitemlayout.scaler

import android.view.View

/**
 * A strategy of scaling particular view
 */
interface ViewScalingStrategy {
    fun scaleView(view: View, scaleFactor: Double)
}