package com.ray650128.mybasemodule.utils

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.EditText


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

fun <T> Collection<T>.copyOf(): Collection<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}

fun <T> Collection<T>.mutableCopyOf(): MutableCollection<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}