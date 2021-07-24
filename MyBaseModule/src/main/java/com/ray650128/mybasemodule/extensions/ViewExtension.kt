package com.ray650128.mybasemodule.extensions

import android.view.View
import android.view.ViewGroup


/**
 * 設定 View 的高度。(單位: px)
 * @param value
 */
fun View.setHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}

/**
 * 設定 View 的寬度。(單位: px)
 * @param value
 */
fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

/**
 * 設定 View 的四邊間距。(單位: px)
 * @param left
 * @param top
 * @param right
 * @param bottom
 */
fun View.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    val lp = layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.setMargins(left, top, right, bottom)
        layoutParams = lp
    }
}