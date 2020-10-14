package ua.com.radiokot.physicalobjectlayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import ua.com.radiokot.physicalobjectlayout.scalingstrategy.CardViewScalingStrategy
import ua.com.radiokot.physicalobjectlayout.scalingstrategy.GeneralViewScalingStrategy
import ua.com.radiokot.physicalobjectlayout.scalingstrategy.TextViewScalingStrategy
import ua.com.radiokot.physicalobjectlayout.scalingstrategy.ViewScalingStrategy
import ua.com.radiokot.physicalobjectlayout.util.forEachChild

/**
 * A layout that properly scales it's children representing physical world objects.
 *
 * [Example image](https://user-images.githubusercontent.com/5675681/95360658-5bbaf480-08d4-11eb-92ec-4bbc6debda14.png)
 *
 * To achieve proper scaling you must create your layout with all sizes and margins
 * fixed and set in px. Without any tricky layouts, without dimens,
 * literally a copy of what the designers sent to you.
 *
 * [Example app](https://github.com/Radiokot/android-physical-object-layout/tree/master/sample-app)
 *
 * @see PhysicalObjectLayout.scaleBy
 * @see ViewScalingStrategy
 *
 * @author [Oleg Koretsky](https://radiokot.com.ua)
 */
open class PhysicalObjectLayout
@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    enum class ScaleDimension {
        WIDTH,
        HEIGHT,
        ;
    }

    /**
     *  Specifies a dimension that children will be scaled to fit to.
     *  Default is [ScaleDimension.WIDTH]
     */
    var scaleBy: ScaleDimension = ScaleDimension.WIDTH
        set(value) {
            val isChanged = field != value
            field = value
            if (isChanged && childCount > 0) {
                requestLayout()
            }
        }

    /**
     * If enabled the layout will set it's children visibility to [View.INVISIBLE] on add.
     * Enabled by default
     */
    var addChildrenInvisible: Boolean

    /**
     * If enabled the layout will set it's children visibility to [View.VISIBLE]
     * once they are scaled.
     * Enabled by default
     */
    var makeChildrenVisibleAfterScale: Boolean

    protected val mScalingStrategies: MutableList<ViewScalingStrategy> = mutableListOf()
    val scalingStrategies: List<ViewScalingStrategy> = mScalingStrategies

    init {
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.PhysicalObjectLayout,
                defStyleAttr,
                0
        ).apply {
            scaleBy = getInteger(R.styleable.PhysicalObjectLayout_pol_scaleBy, scaleBy.ordinal)
                    .let(ScaleDimension.values()::get)
            addChildrenInvisible =
                    getBoolean(R.styleable.PhysicalObjectLayout_pol_addChildrenInvisible, true)
            makeChildrenVisibleAfterScale =
                    getBoolean(R.styleable.PhysicalObjectLayout_pol_makeChildrenVisibleAfterScale, true)

            recycle()
        }

        initDefaultScalingStrategies()
        initLayoutChangeListener()
    }

    private fun initDefaultScalingStrategies() {
        addScalingStrategies(
                GeneralViewScalingStrategy,
                TextViewScalingStrategy,
                CardViewScalingStrategy
        )
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
                ScaleDimension.WIDTH -> {
                    val childWidth = child.width
                    if (childWidth == 0 || currentWidth == 0) {
                        return@forEachChild
                    }
                    currentWidth.toDouble() / childWidth
                }
                ScaleDimension.HEIGHT -> {
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
        scalingStrategies.forEach { scaler ->
            scaler.scaleView(view, scaleFactor)
        }

        if (view is ViewGroup) {
            view.forEachChild { scaleChildView(it, scaleFactor) }
        }

        if (makeChildrenVisibleAfterScale) {
            view.visibility = View.VISIBLE
        }
    }

    fun addScalingStrategies(vararg scalingStrategies: ViewScalingStrategy) {
        mScalingStrategies.addAll(scalingStrategies)
        if (childCount > 0) {
            requestLayout()
        }
    }

    fun addScalingStrategy(scalingStrategy: (view: View, scaleFactor: Double) -> Unit): ViewScalingStrategy {
        return object : ViewScalingStrategy {
            override fun scaleView(view: View, scaleFactor: Double) {
                scalingStrategy(view, scaleFactor)
            }
        }.also { addScalingStrategies(it) }
    }

    fun removeScalingStrategy(scalingStrategy: ViewScalingStrategy) {
        mScalingStrategies.remove(scalingStrategy)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)

        if (addChildrenInvisible) {
            child.visibility = View.INVISIBLE
        }
    }
}