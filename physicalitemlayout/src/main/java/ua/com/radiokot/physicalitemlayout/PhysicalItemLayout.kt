package ua.com.radiokot.physicalitemlayout

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import ua.com.radiokot.physicalitemlayout.util.forEachChild
import kotlin.math.roundToInt

open class PhysicalItemLayout
@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    public enum class ScaleBy {
        WIDTH,
        HEIGHT,
        ;
    }

    protected var scaleBy: ScaleBy
    protected var addChildrenInvisible: Boolean
    protected var makeChildrenVisibleAfterScale: Boolean

    init {
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.PhysicalItemLayout,
                defStyleAttr,
                0
        ).apply {
            scaleBy = getInteger(R.styleable.PhysicalItemLayout_pil_scaleBy, 0)
                    .let(ScaleBy.values()::get)
            addChildrenInvisible =
                    getBoolean(R.styleable.PhysicalItemLayout_pil_addChildrenInvisible, true)
            makeChildrenVisibleAfterScale =
                    getBoolean(R.styleable.PhysicalItemLayout_pil_makeChildrenVisibleAfterScale, true)
        }

        initLayoutChangeListener()
    }

    private fun initLayoutChangeListener() {
        var processedWidth = 0
        var processedHeight = 0

        addOnLayoutChangeListener { _, left, top, right, bottom, _, _, _, _ ->
            val newWidth = right - left
            val newHeight = bottom - top

            if (processedHeight == newHeight && processedWidth == newWidth) {
                return@addOnLayoutChangeListener
            }

            processedWidth = newWidth
            processedHeight = newHeight

            post { scaleChildren(newWidth, newHeight) }
        }
    }

    protected open fun scaleChildren(currentWidth: Int,
                                     currentHeight: Int) {
        forEachChild { child ->
            val scaleFactor = when (scaleBy) {
                ScaleBy.WIDTH -> {
                    val childWidth = child.width
                    if (childWidth == 0 || currentWidth == 0) {
                        return@forEachChild
                    }
                    currentWidth.toDouble() / childWidth
                }
                ScaleBy.HEIGHT -> {
                    val childHeight = child.height
                    if (childHeight == 0 || currentHeight == 0) {
                        return@forEachChild
                    }
                    currentHeight.toDouble() / childHeight
                }
            }

            scaleChildView(child, scaleFactor)
        }
    }

    protected open fun scaleChildView(view: View,
                                      scaleFactor: Double) {
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

            if (this is MarginLayoutParams) {
                if (leftMargin > 0) {
                    leftMargin = (leftMargin * scaleFactor).roundToInt()
                    changesOccurred = true
                }
                if (rightMargin > 0) {
                    rightMargin = (rightMargin * scaleFactor).roundToInt()
                    changesOccurred = true
                }
                if (topMargin > 0) {
                    topMargin = (topMargin * scaleFactor).roundToInt()
                    changesOccurred = true
                }
                if (bottomMargin > 0) {
                    bottomMargin = (bottomMargin * scaleFactor).roundToInt()
                    changesOccurred = true
                }
            }
        }

        if (changesOccurred) {
            view.invalidate()
        }

        when (view) {
            is TextView ->
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.textSize * scaleFactor.toFloat())
            is CardView ->
                view.radius *= scaleFactor.toFloat()
        }

        if (view is ViewGroup) {
            view.forEachChild { scaleChildView(it, scaleFactor) }
        }

        if (makeChildrenVisibleAfterScale) {
            view.visibility = View.VISIBLE
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)

        if (addChildrenInvisible) {
            child.visibility = View.INVISIBLE
        }
    }
}