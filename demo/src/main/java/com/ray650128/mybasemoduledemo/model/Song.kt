package com.ray650128.mybasemoduledemo.model

data class Song(
    var id: Long = 0,
    var title: String = "",
    var artist: String = "",
    var time: Int = 0
) {
    val timeString: String
        get() {
            val minute = time / 1000 / 60
            val s = time / 1000 % 60
            val mm: String = if (minute < 10) "0$minute" else minute.toString() + ""
            val ss: String = if (s < 10) "0$s" else "" + s
            return "$mm:$ss"
        }
}