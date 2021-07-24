package com.ray650128.mybasemodule.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.*
import com.ray650128.mybasemodule.common.R

object AnimUtils {

    enum class AnimType {
        LEFT_TO_RIGHT,  // A:向左滑出 B:向左滑入
        RIGHT_TO_LEFT,  // A:向右滑出 B:向左滑入
        BOTTOM_TO_UP,
        UP_TO_BOTTOM,
        FADE_IN_OUT,    // 淡入淡出
        ROTATION        // 旋轉
    }

    fun showWithAlphaAnim(view: View, duration: Long) {
        view.animate()
                .alpha(1.0f)
                .setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        view.visibility = View.VISIBLE
                    }
                })
    }

    fun hideWithAlphaAnim(view: View, duration: Long) {
        view.animate()
                .alpha(0.0f)
                .setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        view.visibility = View.GONE
                        view.alpha = 1f
                    }
                })
    }

    // 注意：使用動畫必須將物件設成 invisible
    fun showWithSlideUpAnim(view: View, duration: Long) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f,  // fromXDelta
            0f,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0f
        ) // toYDelta

        animate.duration = duration
        animate.fillAfter = false
        view.startAnimation(animate)
    }

    fun showWithSlideDownAnim(view: View, duration: Long) {
        val animate = TranslateAnimation(
            0f,  // fromXDelta
            0f,  // toXDelta
            0f,  // fromYDelta
            view.height.toFloat()
        ) // toYDelta
        animate.duration = duration
        animate.fillAfter = false
        view.startAnimation(animate)
        view.animation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = View.INVISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
    }

    // 注意：使用動畫必須將物件設成 invisible
    fun showWithSlideLeftAnim(view: View, duration: Long) {
        view.visibility = View.VISIBLE
        val animate1 = AnimationUtils.loadAnimation(view.context, R.anim.right_to_left)
        val animate2 = AnimationUtils.loadAnimation(view.context, R.anim.fade_out)
        animate1.duration = duration
        animate2.duration = duration

        val animatorSet = AnimationSet(true).apply {
            fillAfter = true
            addAnimation(animate1)
            addAnimation(animate2)
        }

        view.startAnimation(animatorSet)
    }

    fun showWithPopupAnim(view: View, duration: Long) {
        val amSet = AnimationUtils.loadAnimation(view.context, R.anim.pop_up)
        amSet.duration = duration
        view.startAnimation(amSet)
    }

    // 設定 Activity 轉場動畫
    fun setAnimation(context: Context, animType: AnimType?) {
        val activity: Activity = context as Activity
        when (animType) {
            AnimType.LEFT_TO_RIGHT -> activity.overridePendingTransition(
                R.anim.push_left_in,
                R.anim.push_left_out
            )
            AnimType.RIGHT_TO_LEFT -> activity.overridePendingTransition(
                R.anim.left_to_right,
                R.anim.right_to_left
            )
            AnimType.BOTTOM_TO_UP -> activity.overridePendingTransition(
                R.anim.bottom_to_up,
                R.anim.up_to_bottom
            )
            AnimType.UP_TO_BOTTOM -> activity.overridePendingTransition(
                R.anim.up_to_bottom2,
                R.anim.bottom_to_up2
            )
            AnimType.FADE_IN_OUT -> activity.overridePendingTransition(
                R.anim.fade_in,
                R.anim.fade_out
            )

            AnimType.ROTATION -> activity.overridePendingTransition(
                R.anim.rotatein_out,
                R.anim.rotateout_in
            )
            else -> {
            }
        }
    }
}