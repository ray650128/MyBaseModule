package com.ray650128.mybasemodule.extensions


/**
 * 將集合複製一份，複製出來的集合為唯讀屬性。
 * @return 唯讀的集合
 */
fun <T> Collection<T>.copyOf(): Collection<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}

/**
 * 將集合複製一份，複製出來的集合可任意更改。
 * @return 可變的集合
 */
fun <T> Collection<T>.mutableCopyOf(): MutableCollection<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}