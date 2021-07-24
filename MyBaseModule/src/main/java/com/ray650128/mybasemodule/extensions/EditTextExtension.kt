package com.ray650128.mybasemodule.extensions

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.EditText


/**
 * 將 EditText 的 inputType 改為密碼形式。
 */
fun EditText.setPasswordMode() {
    this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
}

/**
 * 將 EditText 的 inputType 改為純文字形式。
 */
fun EditText.setPlainTextMode() {
    this.inputType = InputType.TYPE_CLASS_TEXT
}

/**
 * 判斷 EditText 的 inputType 是否為密碼形式。
 * @return 是否為密碼形式
 */
fun EditText.isPasswordMode(): Boolean {
    return (this.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
}

/**
 * 在 EditText 的左側加入 icon。
 * @param drawable
 */
fun EditText.setDrawableStart(drawable: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
}

/**
 * 在 EditText 的右側加入 icon。
 * @param drawable
 */
fun EditText.setDrawableEnd(drawable: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
}

/**
 * 在 EditText 的左右兩側加入 icon。
 * @param drawableLeft
 * @param drawableRight
 */
fun EditText.setDrawableStartEnd(drawableLeft: Drawable?, drawableRight: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableLeft, null, drawableRight, null)
}

/**
 * 在 EditText 的右側 icon 加入點擊事件。
 * @param action
 */
@SuppressLint("ClickableViewAccessibility")
fun EditText.onDrawableEndClick(action: (editText: EditText) -> Unit) {
    setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            v as EditText
            val end = if (v.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL)
                v.left else v.right
            if (event.rawX >= (end - v.compoundPaddingEnd)) {
                action.invoke(this)
                return@setOnTouchListener true
            }
        }
        return@setOnTouchListener false
    }
}