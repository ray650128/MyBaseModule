package com.ray650128.mybasemodule.extensions

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.EditText

/**
 * 將 EditText 的文字轉成字串。
 */
fun EditText.getString(): String {
    val text = this.text.toString()
    return if (text.isNotEmpty() && text.last() == ' ') {
        text.dropLast(1)
    } else {
        text
    }
}

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
val EditText.isPasswordMode: Boolean
    get() = (this.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)

/**
 * 在 EditText 的左側加入 icon。
 */
var EditText.drawableStart: Drawable?
    get() = this.compoundDrawables[0]
    set(value) = this.setCompoundDrawablesRelativeWithIntrinsicBounds(value, null, null, null)

/**
 * 在 EditText 的右側加入 icon。
 */
var EditText.drawableEnd: Drawable?
    get() = this.compoundDrawables[2]
    set(value) = this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, value, null)

/**
 * 在 EditText 的左右兩側加入 icon。
 */
var EditText.drawableStartEnd: List<Drawable>?
    get() = listOf(this.compoundDrawables[0], this.compoundDrawables[2])
    set(value) = this.setCompoundDrawablesRelativeWithIntrinsicBounds(
        value?.get(0), null, value?.get(1), null
    )

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