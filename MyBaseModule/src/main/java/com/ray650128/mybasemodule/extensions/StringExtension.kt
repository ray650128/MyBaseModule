package com.ray650128.mybasemodule.extensions

import java.util.regex.Pattern


/**
 * 判斷字串是否為"null"或"NULL"。
 * @return 是否為"null"或"NULL"
 */
val String.isNullString: Boolean
    get() = "null" == trim { it <= ' ' }.lowercase()

/**
 * 判斷參數是否為手機號碼(針對台灣格式)
 * @return 是否為手機號碼
 */
val String.isPhoneNum: Boolean
    get() = Pattern.matches("^[0][9]\\d{8}$", this)

/**
 * 判斷參數是否為身份證號碼(針對台灣格式)
 * @return 是否為身份證號碼
 */
val String.isValidTwPID: Boolean
    get() {
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
 * @return 是否為正確的信箱格式
 */
val String.isEmail: Boolean
    get() {
        val p = Pattern.compile("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$")
        val m = p.matcher(this)
        return m.matches()
    }

/**
 * 判斷是否含有表情符號
 * @return 是否含有表情符號
 */
val String.isContainEmoji: Boolean
    get() {
        val reg = "^([a-z]|[A-Z]|[0-9]|[\u0000-\u00FF]|[\u2000-\uFFFF]){1,}$"
        val pattern = Pattern.compile(reg)
        val matcher = pattern.matcher(this.replace(" ".toRegex(), ""))
        return matcher.matches()
    }

/**
 * 取得檔案名稱(不含副檔名)
 * @return 檔案名稱
 */
val String.getFileName: String?
    get() {
        if (this.isNullString) return null
        val beginIndex = this.lastIndexOf("/")
        val endIndex = this.lastIndexOf(".")
        return this.substring(beginIndex + 1, endIndex)
    }

/**
 * 取得檔案副檔名
 * @return 副檔名
 */
val String.getFileExtension: String
    get() {
        val i = this.lastIndexOf(".") // 返回最後一個點的位置
        return this.substring(i + 1) // 取出副檔名
    }

/**
 * 判斷參數是否為整數
 * @return 是否為整數
 */
val String.isInteger: Boolean
    get() {
        val p = Pattern.compile("[+-]{0,1}0")
        val m = p.matcher(this)
        return m.matches() || this.isPositiveInteger || this.isNegativeInteger
    }

/**
 * 判斷參數是否為正整數
 * @return 是否為正整數
 */
val String.isPositiveInteger: Boolean
    get()  {
        val p = Pattern.compile("^\\+{0,1}[1-9]\\d*")
        val m = p.matcher(this)
        return m.matches()
    }

/**
 * 判斷參數是否為負整數
 * @return 是否為負整數
 */
val String.isNegativeInteger: Boolean
    get() {
        val p = Pattern.compile("^-[1-9]\\d*")
        val m = p.matcher(this)
        return m.matches()
    }

/**
 * 判斷參數是否為浮點數
 * @return 是否為浮點數
 */
val String.isFloat: Boolean
    get() {
        val p = Pattern.compile("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+")
        val m = p.matcher(this)
        return m.matches() || this.isPositiveFloat || this.isNegativeFloat
    }

/**
 * 判斷參數是否為正浮點數
 * @return 是否為正浮點數
 */
val String.isPositiveFloat: Boolean
    get() {
        val p = Pattern.compile("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*")
        val m = p.matcher(this)
        return m.matches()
    }

/**
 * 判斷參數是否為負浮點數
 * @return 是否為負浮點數
 */
val String.isNegativeFloat: Boolean
    get() {
        val p = Pattern.compile("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*")
        val m = p.matcher(this)
        return m.matches()
    }