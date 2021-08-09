package com.ray650128.mybasemodule.extensions

import com.ray650128.mybasemodule.util.StringUtil
import java.util.regex.Pattern


/**
 * 判斷字串是否為"null"或"NULL"。
 * @param str 待判斷的字串
 * @return 是否為"null"或"NULL"
 */
fun String.isNullString(): Boolean {
    return "null" == trim { it <= ' ' }.lowercase()
}

/**
 * 判斷參數是否為手機號碼(針對台灣格式)
 * @param strPhoneNum 待判斷的字串
 * @return 是否為手機號碼
 */
fun String.isPhoneNum(): Boolean {
    return Pattern.matches("^[0][9]\\d{8}$", this)
}

/**
 * 判斷參數是否為身份證號碼(針對台灣格式)
 * @param twpid 待判斷的字串
 * @return 是否為身份證號碼
 */
fun String.isValidTwPID(): Boolean {
    var result = false
    val pattern = "ABCDEFGHJKLMNPQRSTUVXYWZIO"
    if (Pattern.matches("[ABCDEFGHJKLMNPQRSTUVXYWZIO][12]\\d{8}", this)) {
        val code = pattern.indexOf(this.uppercase()[0]) + 10
        val sum =
            code / 10 + 9 * (code % 10) + 8 * (this[1] - '0') + 7 * (this[2] - '0') + 6 * (this[3] - '0') + 5 * (this[4] - '0') + 4 * (this[5] - '0') + 3 * (this[6] - '0') + 2 * (this[7] - '0') + 1 * (this[8] - '0') + (this[9] - '0')
        if (sum % 10 == 0) {
            result = true
        }
    }
    return result
}

/**
 * 判斷是否為正確的信箱格式
 * @param email 待判斷的字串
 * @return 是否為正確的信箱格式
 */
fun isEmail(email: String?): Boolean {
    val p = Pattern.compile("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")
    val m = p.matcher(email)
    return m.matches()
}

/**
 * 判斷是否含有表情符號
 * @param checkString 待判斷的字串
 * @return 是否含有表情符號
 */
fun String.checkEmoji(): Boolean {
    val reg = "^([a-z]|[A-Z]|[0-9]|[\u0000-\u00FF]|[\u2000-\uFFFF]){1,}$"
    val pattern = Pattern.compile(reg)
    val matcher = pattern.matcher(this.replace(" ".toRegex(), ""))
    return matcher.matches()
}

/**
 * 取得檔案名稱(不含副檔名)
 * @param path 檔案完整路徑
 * @return 檔案名稱
 */
fun String.getFileName(): String? {
    if (StringUtil.isNullString(this)) return null
    val bingindex = this.lastIndexOf("/")
    val endindex = this.lastIndexOf(".")
    return this.substring(bingindex + 1, endindex)
}

/**
 * 取得檔案副檔名
 * @param filename 檔案完整名稱或路徑
 * @return 副檔名
 */
fun String.getFileExt(): String {
    val i = this.lastIndexOf(".") // 返回最後一個點的位置
    return this.substring(i + 1) // 取出副檔名
}

/**
 * 判斷參數是否為整數
 * @param input 待判斷的字串
 * @return 是否為整數
 */
fun String.isInteger(): Boolean {
    val p = Pattern.compile("[+-]{0,1}0")
    val m = p.matcher(this)
    return m.matches() || this.isPositiveInteger() || this.isNegativeInteger()
}

/**
 * 判斷參數是否為正整數
 * @param input 待判斷的字串
 * @return 是否為正整數
 */
fun String.isPositiveInteger(): Boolean {
    val p = Pattern.compile("^\\+{0,1}[1-9]\\d*")
    val m = p.matcher(this)
    return m.matches()
}

/**
 * 判斷參數是否為負整數
 * @param input 待判斷的字串
 * @return 是否為負整數
 */
fun String.isNegativeInteger(): Boolean {
    val p = Pattern.compile("^-[1-9]\\d*")
    val m = p.matcher(this)
    return m.matches()
}

/**
 * 判斷參數是否為浮點數
 * @param input 待判斷的字串
 * @return 是否為浮點數
 */
fun String.isFloat(): Boolean {
    val p = Pattern.compile("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+")
    val m = p.matcher(this)
    return m.matches() || this.isPositiveFloat() || this.isNegativeFloat()
}

/**
 * 判斷參數是否為正浮點數
 * @param input 待判斷的字串
 * @return 是否為正浮點數
 */
fun String.isPositiveFloat(): Boolean {
    val p = Pattern.compile("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*")
    val m = p.matcher(this)
    return m.matches()
}

/**
 * 判斷參數是否為負浮點數
 * @param input 待判斷的字串
 * @return 是否為負浮點數
 */
fun String.isNegativeFloat(): Boolean {
    val p = Pattern.compile("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*")
    val m = p.matcher(this)
    return m.matches()
}