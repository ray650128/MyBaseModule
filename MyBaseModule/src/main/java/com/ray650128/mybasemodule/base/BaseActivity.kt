package com.ray650128.mybasemodule.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.ray650128.mybasemodule.databinding.ActivityBaseBinding
import com.ray650128.mybasemodule.extensions.hideKeyboard
import com.ray650128.mybasemodule.extensions.setHeight
import com.ray650128.mybasemodule.util.TransitionUtil
import com.ray650128.mybasemodule.view.ProgressView
import java.lang.reflect.ParameterizedType


/**
 * Activity 基本類別
 * 本類別基於 ViewBinding，將 AppCompatActivity 進行封裝。
 * 並且客製化一個標題置中的 Toolbar，以及實作了 Toolbar 的一些設定方法。
 * 除此之外，針對常見需求加入了一些實用方法來減少程式碼重複的情況發生，例如：
 *   顯示/隱藏虛擬鍵盤
 *   顯示/隱藏載入中畫面
 *   顯示簡易的訊息對話視窗
 *   跳轉Activity等
 * 要使用 UI 元件，請使用 binding.<UI元件名稱>。
 * @author Raymond Yang
 */
@SuppressLint("ClickableViewAccessibility")
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var baseBinding: ActivityBaseBinding

    lateinit var binding: T

    private var mProgressView: ProgressView? = null

    private var toast: Toast? = null

    abstract val useCustomActionBar: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)

        if (useCustomActionBar) {
            baseBinding.toolbarTitle.text = title
            setToolbarVisible(true)
            setSupportActionBar(baseBinding.toolbar)

            supportActionBar?.setDisplayShowTitleEnabled(false)
        } else {
            setToolbarVisible(false)
        }

        // 利用反射，呼叫指定 ViewBinding 中的 inflate 方法填充 View
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments[0] as Class<T>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            binding = method.invoke(null, layoutInflater) as T
            baseBinding.root.addView(
                binding.root,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            hideKeyboard(currentFocus!!)
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 是否顯示 Toolbar
     * @param isVisible  是否顯示。true=顯示、false=不顯示
     */
    fun setToolbarVisible(isVisible: Boolean) {
        baseBinding.toolbar.isVisible = isVisible
    }

    /**
     * 設定 Toolbar 的背景圖片
     * @param resId  Toolbar 的背景圖片資源
     */
    fun setToolbarBackgroundResource(@DrawableRes resId: Int) {
        baseBinding.toolbar.setBackgroundResource(resId)
    }

    /**
     * 設定 Toolbar 的背景色
     * @param colorInt  Toolbar 的色
     */
    fun setToolbarBackgroundColor(@ColorInt colorInt: Int) {
        baseBinding.toolbar.setBackgroundColor(colorInt)
    }

    /**
     * 設定 Toolbar 的高度，若需顯示 Toolbar 的陰影，請將高度設定高於0
     * @param elevation  Toolbar 的高度
     */
    fun setToolbarElevation(elevation: Float) {
        baseBinding.toolbar.elevation = elevation
    }

    /**
     * 設定 Toolbar 的文字色彩
     * @param colorInt  Toolbar 的文字色彩
     */
    fun setToolbarTextColor(@ColorInt colorInt: Int) {
        baseBinding.toolbarTitle.setTextColor(colorInt)
    }

    /**
     * 設定 Toolbar 的文字色彩
     * @param colors  Toolbar 的文字色彩
     */
    fun setToolbarTextColor(colors: ColorStateList) {
        baseBinding.toolbarTitle.setTextColor(colors)
    }

    /**
     * 取得 Toolbar 的標題標籤
     * @return TextView
     */
    fun getToolbarTextLabel(): TextView {
        return baseBinding.toolbarTitle
    }

    /**
     * 設定 Toolbar 的高度
     * @param height  Toolbar 的高度(像素)
     */
    fun setToolbarHeight(height: Int) {
        baseBinding.toolbar.setHeight(height)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        baseBinding.toolbarTitle.text = title
    }

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        baseBinding.toolbarTitle.text = getString(titleId)
    }

    /**
     * 顯示載入畫面
     */
    fun showLoadingDialog() {
        if (mProgressView == null) {
            mProgressView = ProgressView(this)
        }
        mProgressView?.showLoading()
    }

    /**
     * 隱藏載入畫面
     */
    fun hideLoadingDialog() {
        if (mProgressView != null && mProgressView!!.isShowing) {
            mProgressView?.hideLoading()
        }
    }

    /**
     * 顯示訊息泡泡(時間較短)
     * @param resId  字串資源
     */
    fun showToastShort(@StringRes resId: Int) {
        showToast(getString(resId), Toast.LENGTH_SHORT)
    }

    /**
     * 顯示訊息泡泡(時間較短)
     * @param msg  字串
     */
    fun showToastShort(msg: String) {
        showToast(msg, Toast.LENGTH_SHORT)
    }

    /**
     * 顯示訊息泡泡(時間較長)
     * @param resId  字串資源
     */
    fun showToastLong(@StringRes resId: Int) {
        showToast(getString(resId), Toast.LENGTH_LONG)
    }

    /**
     * 顯示訊息泡泡(時間較長)
     * @param msg  字串
     */
    fun showToastLong(msg: String) {
        showToast(msg, Toast.LENGTH_LONG)
    }

    private fun showToast(msg: String, duration: Int) {
        if (toast == null) {
            //如果還沒有用過makeText方法，才使用
            toast = Toast.makeText(this, msg, duration)
        } else {
            toast!!.setText(msg)
            toast!!.duration = duration
        }
        toast!!.show()
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        TransitionUtil.setTransitionEffect(this, TransitionUtil.TransitionType.LEFT_TO_RIGHT)
    }

    override fun finish() {
        super.finish()
        TransitionUtil.setTransitionEffect(this, TransitionUtil.TransitionType.RIGHT_TO_LEFT)
    }
}