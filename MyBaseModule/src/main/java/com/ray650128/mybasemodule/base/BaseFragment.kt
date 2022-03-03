package com.ray650128.mybasemodule.base

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ray650128.mybasemodule.util.TransitionUtil
import com.ray650128.mybasemodule.view.ProgressView
import java.lang.reflect.ParameterizedType


/**
 * BaseFragment 基本類別
 * 本類別基於 ViewBinding，將 Fragment 進行封裝。
 * 要使用 UI 元件，請使用 binding.<UI元件名稱>。
 * @author Raymond Yang
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    lateinit var binding : T

    private lateinit var mContext: Context

    private var mProgressView: ProgressView? = null

    private var toast: Toast? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 利用反射，呼叫指定 ViewBinding 中的 inflate 方法填充 View
        val type = javaClass.genericSuperclass as ParameterizedType
        val mClass = type.actualTypeArguments[0] as Class<T>
        val method = mClass.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        binding = method.invoke(null, layoutInflater, container, false) as T
        return binding.root
    }

    /**
     * 顯示虛擬鍵盤
     * @param view  鍵盤的焦點
     */
    fun showKeyboard(view: EditText?) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        view?.isCursorVisible = true
    }

    /**
     * 隱藏虛擬鍵盤
     * @param view
     */
    fun hideKeyboard(view: View) {
        val imm: InputMethodManager = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 顯示載入畫面
     */
    fun showDialogLoading() {
        if (mProgressView == null) {
            mProgressView = ProgressView(mContext)
        }
        mProgressView?.showLoading()
    }

    /**
     * 隱藏載入畫面
     */
    fun hideDialogLoading() {
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
    fun showMessageDialog(@StringRes resId: Int,
                          onPositivePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null,
                          onNegativePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null) {
        val dialog = AlertDialog.Builder(mContext)
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
        val dialog = AlertDialog.Builder(mContext)
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
            toast = Toast.makeText(requireActivity(), msg, duration)
        } else {
            toast!!.setText(msg)
            toast!!.duration = duration
        }
        toast!!.show()
    }

    /**
     * 跳轉到其他 Activity
     * @param activity   目標 Activity
     * @param bundle     傳遞的資料
     * @param closeSelf  跳轉後是否關閉自己
     */
    fun <A> gotoActivity(activity: Class<A>, bundle: Bundle? = null, closeSelf: Boolean = false) {
        val intent = Intent(requireActivity(), activity)
        if (bundle != null) intent.putExtras(bundle)
        startActivity(intent)
        startAnimation()
        if (closeSelf) {
            requireActivity().finish()
        }
    }

    /**
     * 跳轉到系統瀏覽 Activity
     * @param uri        目標 Uri
     * @param closeSelf  跳轉後是否關閉自己
     */
    fun gotoViewerActivity(uri: Uri?, closeSelf: Boolean = false) {
        if (uri == null) return
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
        if (closeSelf) {
            requireActivity().finish()
        }
    }

    /**
     * 跳轉到其他 Activity，並將所有的 Activity 從任務堆疊中移除
     * @param activity   目標 Activity
     * @param bundle     傳遞的資料
     */
    fun <A> gotoActivityAndClearStack(activity: Class<A>, bundle: Bundle? = null) {
        val intent = Intent(requireActivity(), activity)
        if (bundle != null) intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        startAnimation()
    }

    open fun startAnimation() {
        TransitionUtil.setTransitionEffect(mContext, TransitionUtil.TransitionType.LEFT_TO_RIGHT)
    }
}