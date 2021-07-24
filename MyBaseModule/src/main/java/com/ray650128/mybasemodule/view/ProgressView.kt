package com.ray650128.mybasemodule.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.ray650128.mybasemodule.R

/**
 * 讀取提示畫面
 */
class ProgressView constructor(context: Context) : Dialog(context, R.style.LightProgressDialog) {

    private var animDrawable: AnimationDrawable? = null

    init {
        val layout = LinearLayout(context).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(40, 40, 40, 40)
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundColor(Color.TRANSPARENT)
        }
        val view = ProgressBar(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 1f
            }
        }
        layout.addView(view)

        setContentView(layout)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    /**
     * 顯示對話框
     */
    fun showLoading() {
        if (!this.isShowing) {
            this.show()
        }
        animDrawable?.start()
    }

    /**
     * 隱藏對話框
     */
    fun hideLoading() {
        if (this.isShowing) {
            this.dismiss()
        }
        animDrawable?.stop()
    }
}