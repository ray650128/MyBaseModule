package com.ray650128.mybasemodule.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
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
}