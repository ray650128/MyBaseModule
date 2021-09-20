package com.ray650128.mybasemodule.util

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * 畫面尺寸相關工具類別。
 */
class DensityUtil(private var context: Context) {

    /**
     * 取得螢幕寬度
     * @return 螢幕寬度(px)
     */
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

    /**
     * 取得螢幕高度
     * @return 螢幕高度(px)
     */
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

    /**
     * 取得狀態列高度
     * @return 狀態列高度(px)
     */
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

    /**
     * 取得導航列高度
     * @return 導航列高度(px)
     */
    fun getNavigationBarHeight(): Int {
        val resources = context.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }
}