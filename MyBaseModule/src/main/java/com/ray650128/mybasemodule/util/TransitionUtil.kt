package com.ray650128.mybasemodule.util

import android.app.Activity
import android.content.Context
import com.ray650128.mybasemodule.R

object TransitionUtil {

    enum class TransitionType {
        LEFT_TO_RIGHT,  // A:向左滑出 B:向左滑入
        RIGHT_TO_LEFT,  // A:向右滑出 B:向左滑入
        BOTTOM_TO_UP,
        UP_TO_BOTTOM,
        FADE_IN_OUT,    // 淡入淡出
        ROTATION        // 旋轉
    }

    // 設定 Activity 轉場動畫
    fun setTransitionEffect(context: Context, animType: TransitionType?) {
        val activity: Activity = context as Activity
        when (animType) {
            TransitionType.LEFT_TO_RIGHT -> activity.overridePendingTransition(
                R.anim.push_left_in,
                R.anim.push_left_out
            )
            TransitionType.RIGHT_TO_LEFT -> activity.overridePendingTransition(
                R.anim.left_to_right,
                R.anim.right_to_left
            )
            TransitionType.BOTTOM_TO_UP -> activity.overridePendingTransition(
                R.anim.bottom_to_up,
                R.anim.up_to_bottom
            )
            TransitionType.UP_TO_BOTTOM -> activity.overridePendingTransition(
                R.anim.up_to_bottom2,
                R.anim.bottom_to_up2
            )
            TransitionType.FADE_IN_OUT -> activity.overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )

            TransitionType.ROTATION -> activity.overridePendingTransition(
                R.anim.rotatein_out,
                R.anim.rotateout_in
            )
            else -> {
            }
        }
    }
}