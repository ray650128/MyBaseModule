package com.ray650128.mybasemodule.viewModelUtils

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ViewModelField(val scopeName: String) {
}