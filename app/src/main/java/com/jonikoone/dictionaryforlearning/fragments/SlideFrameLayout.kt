package com.jonikoone.dictionaryforlearning.fragments

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import timber.log.Timber


open class SlideFrameLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var downX: Float? = null
    var upX: Float? = null
    val deltaX = 50F

    var actionBySlide: OnActionBySlide? = null

    init {
        isClickable = true
        this.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                }
                MotionEvent.ACTION_MOVE -> {
                    // track to finger
                    this.layout(event.x.toInt(), top, right, bottom)
                    Timber.d("SlideFrameLayout: move x: ${event.x}")
                }
                MotionEvent.ACTION_UP -> {
                    upX = event.x
                    if (upX!! - downX!! > deltaX) {
                        //close
                        actionBySlide?.onClose()
                    } else {
                        //nothing, to the first time
                        actionBySlide?.onCancel()
                    }
                }
            }

            true
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }


    interface OnActionBySlide {
        fun onCancel() {
            Timber.d("SlideFrameLayout.OnActionBySlide: try cancel")
        }
        fun onClose() {
            Timber.d("SlideFrameLayout.OnActionBySlide: try close")
        }
    }

}