package com.ray650128.mybasemodule.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.ray650128.mybasemodule.databinding.ActivityBaseBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        baseBinding.root.setOnTouchListener { view, _ ->
            hideKeyboard(view)
            false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val textTemp = "<font color= '#000' font size='18'>$title</font>"
            val text = Html.fromHtml(textTemp, HtmlCompat.FROM_HTML_MODE_LEGACY)
            baseBinding.toolbarTitle.text = text
        } else {
            baseBinding.toolbarTitle.text = title
        }
        setContentView(baseBinding.root)
        setSupportActionBar(baseBinding.toolbar)

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

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
     * 取得 Toolbar 的標題標籤
     * @return TextView
     */
    fun getToolbarTextLabel(): TextView {
        return baseBinding.toolbarTitle
    }

    /**
     * 設定 Toolbar 的文字色彩
     * @param colors  Toolbar 的文字色彩
     */
    fun setToolbarTextColor(colors: ColorStateList) {
        baseBinding.toolbarTitle.setTextColor(colors)
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
     * 顯示虛擬鍵盤
     * @param view  鍵盤的焦點
     */
    fun showKeyboard(view: EditText?) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        view?.isCursorVisible = true
    }

    /**
     * 隱藏虛擬鍵盤
     * @param view
     */
    fun hideKeyboard(view: View) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 隱藏虛擬鍵盤
     * @param activity
     */
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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
     * 顯示訊息對話框
     * @param resId  字串資源
     * @param onPositivePress  按下確定時的動作，預設 null=按下後關閉
     * @param onNegativePress  按下取消時的動作，預設 null=不顯示
     */
    fun showMessageDialog(
        @StringRes resId: Int,
        onPositivePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null,
        onNegativePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null
    ) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(resId)
        dialog.setPositiveButton(android.R.string.ok) { thisDlg, which ->
            onPositivePress?.invoke(thisDlg, which)
            thisDlg.dismiss()
        }
        if (onNegativePress != null) {
            dialog.setNegativeButton(android.R.string.cancel) { thisDlg, which ->
                onNegativePress.invoke(thisDlg, which)
                thisDlg.dismiss()
            }
        }
        dialog.show()
    }

    /**
     * 顯示訊息對話框
     * @param msg  字串
     * @param onPositivePress  按下確定時的動作，預設 null=按下後關閉
     * @param onNegativePress  按下取消時的動作，預設 null=不顯示
     */
    fun showMessageDialog(
        msg: String,
        onPositivePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null,
        onNegativePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null
    ) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(msg)
            dialog.setPositiveButton(android.R.string.ok) { thisDlg, which ->
                onPositivePress?.invoke(thisDlg, which)
                thisDlg.dismiss()
        }
        if (onNegativePress != null) {
            dialog.setNegativeButton(android.R.string.cancel) { thisDlg, which ->
                onNegativePress.invoke(thisDlg, which)
                thisDlg.dismiss()
            }
        }
        dialog.show()
    }

    /**
     * 跳轉到其他 Activity
     * @param activity   目標 Activity
     * @param bundle     傳遞的資料
     * @param closeSelf  跳轉後是否關閉自己
     */
    fun <A> gotoActivity(activity: Class<A>, bundle: Bundle? = null, closeSelf: Boolean = false) {
        val intent = Intent(this, activity)
        if (bundle != null) intent.putExtras(bundle)
        startActivity(intent)
        if (closeSelf) {
            finish()
        }
        TransitionUtil.setTransitionEffect(this, TransitionUtil.TransitionType.LEFT_TO_RIGHT)
    }

    /**
     * 跳轉到其他 Activity，並將所有的 Activity 從任務堆疊中移除
     * @param activity   目標 Activity
     * @param bundle     傳遞的資料
     */
    fun <A> gotoActivityAndClearStack(activity: Class<A>, bundle: Bundle? = null) {
        val intent = Intent(this, activity)
        if (bundle != null) intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        TransitionUtil.setTransitionEffect(this, TransitionUtil.TransitionType.LEFT_TO_RIGHT)
    }

    override fun finish() {
        super.finish()
        TransitionUtil.setTransitionEffect(this, TransitionUtil.TransitionType.RIGHT_TO_LEFT)
    }
}