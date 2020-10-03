package ua.com.radiokot.physicalitemlayout.util

import android.view.View
import android.view.ViewGroup

/**
 * Execute [action] for each child of the received [ViewGroup].
 *
 * @param action the action to execute.
 */
internal inline fun ViewGroup.forEachChild(action: (View) -> Unit) {
    for (i in 0 until childCount) {
        action(getChildAt(i))
    }
}