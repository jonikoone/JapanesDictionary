package com.jonikoone.dictionaryforlearning.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ScrollAwareFABBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<FloatingActionButton?>() {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int
    ): Boolean {
        return true
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed
        )
        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            child.hide()
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            child.show()
        }
    }

    /*override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?,
        directTargetChild: View?, target: View?, nestedScrollAxes: Int
    ): Boolean {
        return true
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout?,
        child: FloatingActionButton,
        target: View?, dxConsumed: Int, dyConsumed: Int,
        dxUnconsumed: Int, dyUnconsumed: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout!!,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed
        )
        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            child.hide()
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            child.show()
        }
    }*/
}