package com.ray650128.mybasemodule.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.*
import androidx.core.view.isVisible
import com.ray650128.mybasemodule.R


/**
 * View 動畫工具類別。
 */
object AnimationUtil {

    /**
     * 顯示淡入動畫。
     * @param view
     * @param duration
     */
    fun fadeIn(view: View, duration: Long) {
        view.isVisible = true
        view.alpha = 0.0f
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

    /**
     * 顯示淡出動畫。
     * @param view
     * @param duration
     */
    fun fadeOut(view: View, duration: Long) {
        view.isVisible = true
        view.alpha = 1.0f
        view.animate()
            .alpha(0.0f)
            .setDuration(duration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view.isVisible = false
                    view.alpha = 1f
                }
            })
    }

    /**
     * 由下往上滑入。
     * @param view
     * @param duration
     */
    fun slideUp(view: View, duration: Long) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, view.height.toFloat(), 0f)
        animate.duration = duration
        animate.fillAfter = false
        view.startAnimation(animate)
    }

    /**
     * 由上往下滑入。
     * @param view
     * @param duration
     */
    fun slideDown(view: View, duration: Long) {
        val animate = TranslateAnimation(0f, 0f, 0f, view.height.toFloat())
        animate.duration = duration
        animate.fillAfter = false
        view.startAnimation(animate)
        view.animation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
    }

    /**
     * 由右往左滑入。
     * @param view
     * @param duration
     */
    fun slideLeft(view: View, duration: Long) {
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

    /**
     * Popup 動畫。
     * @param view
     * @param duration
     */
    fun showWithPopupAnim(view: View, duration: Long) {
        val amSet = AnimationUtils.loadAnimation(view.context, R.anim.pop_up)
        amSet.duration = duration
        view.startAnimation(amSet)
    }
}