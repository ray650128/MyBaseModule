package com.ray650128.mybasemodule.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.*
import com.ray650128.mybasemodule.R

object AnimationUtil {

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
}