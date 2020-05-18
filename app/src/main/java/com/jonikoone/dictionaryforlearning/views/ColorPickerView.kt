package com.jonikoone.dictionaryforlearning.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import com.jonikoone.dictionaryforlearning.R
import okhttp3.internal.toHexString
import timber.log.Timber
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class ColorPickerView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        this.attrs = attrs
    }

    private var attrs: AttributeSet? = null
    private val scale by lazy {
        context.resources.displayMetrics.density
    }

    private val Int.dp: Float
        get () = this * scale

    var colorListener: ((newColor: Int) -> Unit)? = null
    var colorManyChangeListener: ((newColor: Int) -> Unit)? = null

    private val MIN_HEIGHT = 224.dp

    private val ANIMATION_DURATION = 400L

    private var selectedColor: Int = 0

    private var widthRect by Delegates.notNull<Int>()
    private var heigthRect by Delegates.notNull<Int>()

    private val backgroundRect = RectF()
    private val foregroundColorLabelRect = RectF()

    private var touchX = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var touchY = 0f
        set(value) {
            field = value
            invalidate()
        }

    val backgroundColorPaint by lazy {
        Paint().apply {
            val delta: Float = widthRect / 6f
            val shaderTone = LinearGradient(
                0f, 0f, 0f, heigthRect.toFloat(),
                intArrayOf(
                    0xffffffff.toInt(),
                    0xff000000.toInt()
                ),
                floatArrayOf(0f, 1f),
                Shader.TileMode.CLAMP
            )

            val shaderBase = LinearGradient(
                0f, 0f, widthRect.toFloat(), 0f,
                intArrayOf(
                    0xffff0000.toInt(),
                    0xffffff00.toInt(),
                    0xff00ff00.toInt(),
                    0xff00ffff.toInt(),
                    0xff0000ff.toInt(),
                    0xffff00ff.toInt(),
                    0xffff0000.toInt()
                ),
                floatArrayOf(
                    0f,
                    1 / 6f,
                    2 / 6f,
                    3 / 6f,
                    4 / 6f,
                    5 / 6f,
                    1f
                ), Shader.TileMode.CLAMP
            )


            val composeShader = ComposeShader(shaderBase, shaderTone, PorterDuff.Mode.MULTIPLY)
            this.shader = composeShader
        }
    }
    val foregroundColorLabelPaint = Paint().apply {
        alpha = 0
    }

    private val zoneColor by lazy { width / 6 }

    init {
        attrs?.let {
            selectedColor = it.getAttributeIntValue(R.styleable.ColorPickerView_init_color, 0)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sizeW = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(sizeW, MIN_HEIGHT.toInt())
        widthRect = sizeW
        heigthRect = MIN_HEIGHT.toInt()
        backgroundRect.set(0f, 0f, widthRect.toFloat(), heigthRect.toFloat())
        foregroundColorLabelRect.set(0f, 0f, widthRect.toFloat(), heigthRect.toFloat())
    }



    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawRect(backgroundRect, backgroundColorPaint)
        canvas?.drawRect(foregroundColorLabelRect, foregroundColorLabelPaint)
    }

    override fun performClick(): Boolean {
        performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean = when(event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
            touchX = event.x
            touchY = event.y
            parent.requestDisallowInterceptTouchEvent(true)
            foregroundColorLabelPaint.color = backgroundRect.getColorFromTouch(touchX, touchY)
            colorManyChangeListener?.invoke(foregroundColorLabelPaint.color or (0xff shl 24))
            showColorLabel()
            true
        }
        MotionEvent.ACTION_MOVE -> {
            val x = event.x
            val y = event.y
            if (backgroundRect.contains(x, y)) {
                touchX = x
                touchY = y
                foregroundColorLabelPaint.color = backgroundRect.getColorFromTouch(touchX, touchY)
                colorManyChangeListener?.invoke(foregroundColorLabelPaint.color or (0xff shl 24))
            }
            true
        }

        MotionEvent.ACTION_UP,
        MotionEvent.ACTION_CANCEL -> {
            hideColorLabel()
            colorListener?.invoke(foregroundColorLabelPaint.color)
            performClick()
            true
        }

        else -> false
    }

    private fun createColor(nZone: Int, x: Float, block: (addedColor: Int) -> Int): Int{
        val xx = x - (zoneColor * nZone)
        Timber.d("ColorPicker: create color for zone: $nZone - $xx, when x=$x")
        val addedColor: Int = (0xff * (xx / zoneColor)).roundToInt()
        return block(addedColor)
    }

    fun RectF.getColorFromTouch(x: Float, y: Float): Int {
        val yDelta = 1 - (y / height) // 0 <= y <= 1
        //val xDelta = x / width // 0 <= x <= 1

        val color = when {
            x >= 0 && x < zoneColor -> createColor(0, x) {
                ((0x00 or (0xff * yDelta).toInt()) shl 16) or ((0x00 or (it * yDelta).toInt()) shl 8)
            }
            x >= zoneColor && x < zoneColor * 2 -> createColor(1, x) {
                ((0x00 or ((0xff - it) * yDelta).toInt()) shl 16) or ((0x00 or (0xff * yDelta).toInt()) shl 8)
            }
            x >= zoneColor * 2 && x < zoneColor * 3 -> createColor(2, x) {
                (0x00 shl 16) or ((0xff * yDelta).toInt() shl 8) or (0x00 or (it * yDelta).toInt())
            }
            x >= zoneColor * 3 && x < zoneColor * 4 -> createColor(3, x) {
                (0x00 shl 16) or ((0x00 or ((0xff - it) * yDelta).toInt()) shl 8) or (0x00 or (0xff * yDelta).toInt())
            }
            x >= zoneColor * 4 && x < zoneColor * 5 -> createColor(4, x) {
                ((0x00 or (it * yDelta).toInt()) shl 16) or (0x00 shl 8) or (0xff * yDelta).toInt()
            }
            x >= zoneColor * 5 && x < zoneColor * 6 -> createColor(5, x) {
                (((0xff * yDelta).toInt()) shl 16) or (0x00 shl 8) or (0x00 or ((0xff - it) * yDelta).toInt())
            }

            else -> 0x000000
        }
        Timber.d("ColorPicker: getColorFromTouch: #${color.toHexString()}")
        return (foregroundColorLabelPaint.alpha shl 24) or color
    }

    fun showColorLabel() {
        val animation = ValueAnimator.ofInt(foregroundColorLabelPaint.alpha, 0xff)
        animation.duration = ANIMATION_DURATION
        animation.addUpdateListener {
            foregroundColorLabelPaint.alpha = it.animatedValue as Int
            invalidate()
        }
        animation.start()
    }

    fun hideColorLabel() {
        val animation = ValueAnimator.ofInt(foregroundColorLabelPaint.alpha, 0)
        animation.duration = ANIMATION_DURATION
        animation.addUpdateListener {
            foregroundColorLabelPaint.alpha = it.animatedValue as Int
            invalidate()
        }
        animation.start()
    }

}

