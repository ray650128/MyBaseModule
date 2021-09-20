package com.ray650128.mybasemodule.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.core.view.isVisible
import com.ray650128.mybasemodule.R


/**
 * View 動畫工具類別。
 */
object AnimationUtil {

    /**
     * 播放淡入動畫。
     * @param   view        指定的 UI 元件
     * @param   duration    播放時間長度
     * @return  無
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
     * 播放淡出動畫。
     * @param   view        指定的 UI 元件
     * @param   duration    播放時間長度
     * @return  無
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
     * 播放由下往上滑入動畫。
     * 注意：使用動畫必須將物件設成 invisible
     * @param   view        指定的 UI 元件
     * @param   duration    播放時間長度
     * @return  無
     */
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

    /**
     * 播放由上往下滑出動畫。
     * @param   view        指定的 UI 元件
     * @param   duration    播放時間長度
     * @return  無
     */
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

    /**
     * 播放由左側滑入動畫。
     * 注意：使用動畫必須將物件設成 invisible
     * @param   view        指定的 UI 元件
     * @param   duration    播放時間長度
     * @return  無
     */
    fun showWithSlideLeftAnim(view: View, duration: Long): Animation {
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
        return animatorSet
    }

    /**
     * 播放由右側滑入動畫。
     * 注意：使用動畫必須將物件設成 invisible
     * @param   view        指定的 UI 元件
     * @param   duration    播放時間長度
     * @return  無
     */
    fun showWithSlideRightAnim(view: View, duration: Long): Animation {
        view.visibility = View.VISIBLE
        val animate1 = AnimationUtils.loadAnimation(view.context, R.anim.left_to_right)
        val animate2 = AnimationUtils.loadAnimation(view.context, R.anim.fade_out)
        animate1.duration = duration
        animate2.duration = duration

        val animatorSet = AnimationSet(true).apply {
            fillAfter = true
            addAnimation(animate1)
            addAnimation(animate2)
        }

        view.startAnimation(animatorSet)
        return animatorSet
    }

    /**
     * 播放 PopUp 動畫。
     * @param   view        指定的 UI 元件
     * @param   duration    播放時間長度
     * @return  無
     */
    fun showWithPopupAnim(view: View, duration: Long): Animation {
        val amSet = AnimationUtils.loadAnimation(view.context, R.anim.pop_up)
        amSet.duration = duration
        view.visibility = View.VISIBLE
        view.startAnimation(amSet)
        return amSet
    }
}