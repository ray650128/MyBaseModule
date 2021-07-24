package com.ray650128.mybasemodule.utils

import android.graphics.drawable.Drawable
import android.text.InputType
import android.widget.EditText

fun EditText.setPasswordMode() {
    this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
}

fun EditText.setPlainTextMode() {
    this.inputType = InputType.TYPE_CLASS_TEXT
}

fun EditText.isPasswordMode(): Boolean {
    return (this.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
}

fun EditText.setDrawableStart(drawable: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
}

fun EditText.setDrawableEnd(drawable: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
}

fun EditText.setDrawableStartEnd(drawable1: Drawable?, drawable2: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable1, null, drawable2, null)
}
