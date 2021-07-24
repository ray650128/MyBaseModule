package com.ray650128.mybasemodule.viewModelUtils

/**
 * 標示 ViewModel 的作用域
 * @author     Raymond Yang
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ViewModelField(val scopeName: String)