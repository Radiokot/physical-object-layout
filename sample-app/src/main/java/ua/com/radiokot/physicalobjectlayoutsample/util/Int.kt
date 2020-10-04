package ua.com.radiokot.physicalobjectlayoutsample.util

import android.content.res.Resources

val Int.dp
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()