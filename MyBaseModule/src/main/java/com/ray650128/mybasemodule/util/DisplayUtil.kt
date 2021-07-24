package com.ray650128.mybasemodule.util

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import kotlin.math.roundToInt


class DisplayUtils(private var context: Context) {

    fun spToPx(spValue: Int): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f)
    }

    fun dpToPx(dps: Int): Int {
        return (context.resources.displayMetrics.density * dps).roundToInt()
    }

    fun getScreenWidth(): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            windowMetrics.bounds.width()
        } else {
            val metric = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metric)
            metric.widthPixels
        }
    }

    fun getScreenHeight(): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            windowMetrics.bounds.height()
        } else {
            val metric = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metric)
            metric.heightPixels
        }
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId: Int = context.resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        )
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getNavigationBarHeight(): Int {
        val resources = context.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getWindowHeight(): Int {
        val screen = getScreenHeight()
        val statusBar = getStatusBarHeight()
        val navBar = getNavigationBarHeight()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            screen - statusBar - navBar
        } else {
            screen
        }
    }
}

fun View.setHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}

fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    val lp = layoutParams
    if (lp is MarginLayoutParams) {
        lp.setMargins(left, top, right, bottom)
        layoutParams = lp
    }
}