package com.ray650128.mybasemodule.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


/**
 * BaseFragment 基本類別
 * 本類別基於 ViewBinding，將 Fragment 進行封裝。
 * 要使用 UI 元件，請使用 binding.<UI元件名稱>。
 * @author Raymond Yang
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding : T? = null
    val binding :T get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 利用反射，呼叫指定 ViewBinding 中的 inflate 方法填充 View
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<T>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        _binding = method.invoke(null, layoutInflater, container, false) as T
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    abstract fun init()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}