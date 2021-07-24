package com.ray650128.mybasemodule.util

import java.util.*
import java.util.regex.Pattern

/**
 * 字串工具類別。
 */
object StringUtil {
    /**
     * 判斷字串是否為"null"或"NULL"。
     * @param str 待判斷的字串
     * @return 是否為"null"或"NULL"
     */
    fun isNullString(str: String): Boolean {
        return "null" == str.trim { it <= ' ' }.lowercase()
    }

    /**
     * 判斷參數是否為手機號碼(針對台灣格式)
     * @param strPhoneNum 待判斷的字串
     * @return 是否為手機號碼
     */
    fun isPhoneNum(strPhoneNum: String?): Boolean {
        return Pattern.matches("^[0][9]\\d{8}$", strPhoneNum)
    }

    /**
     * 判斷參數是否為身份證號碼(針對台灣格式)
     * @param twpid 待判斷的字串
     * @return 是否為身份證號碼
     */
    fun isValidTwPID(twpid: String): Boolean {
        var result = false
        val pattern = "ABCDEFGHJKLMNPQRSTUVXYWZIO"
        if (Pattern.matches("[ABCDEFGHJKLMNPQRSTUVXYWZIO][12]\\d{8}", twpid)) {
            val code = pattern.indexOf(twpid.uppercase()[0]) + 10
            val sum =
                code / 10 + 9 * (code % 10) + 8 * (twpid[1] - '0') + 7 * (twpid[2] - '0') + 6 * (twpid[3] - '0') + 5 * (twpid[4] - '0') + 4 * (twpid[5] - '0') + 3 * (twpid[6] - '0') + 2 * (twpid[7] - '0') + 1 * (twpid[8] - '0') + (twpid[9] - '0')
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
    fun checkEmoji(checkString: String): Boolean {
        val reg = "^([a-z]|[A-Z]|[0-9]|[\u0000-\u00FF]|[\u2000-\uFFFF]){1,}$"
        val pattern = Pattern.compile(reg)
        val matcher = pattern.matcher(checkString.replace(" ".toRegex(), ""))
        return matcher.matches()
    }

    /**
     * 取得檔案名稱(不含副檔名)
     * @param path 檔案完整路徑
     * @return 檔案名稱
     */
    fun getFileName(path: String): String? {
        if (isNullString(path)) return null
        val bingindex = path.lastIndexOf("/")
        val endindex = path.lastIndexOf(".")
        return path.substring(bingindex + 1, endindex)
    }

    /**
     * 取得檔案副檔名
     * @param filename 檔案完整名稱或路徑
     * @return 副檔名
     */
    fun getFileExt(filename: String): String {
        val i = filename.lastIndexOf(".") // 返回最後一個點的位置
        return filename.substring(i + 1) // 取出副檔名
    }

    /**
     * 判斷參數是否為整數
     * @param input 待判斷的字串
     * @return 是否為整數
     */
    fun isInteger(input: String): Boolean {
        val p = Pattern.compile("[+-]{0,1}0")
        val m = p.matcher(input)
        return m.matches() || isPositiveInteger(input) || isNegativeInteger(input)
    }

    /**
     * 判斷參數是否為正整數
     * @param input 待判斷的字串
     * @return 是否為正整數
     */
    fun isPositiveInteger(input: String): Boolean {
        val p = Pattern.compile("^\\+{0,1}[1-9]\\d*")
        val m = p.matcher(input)
        return m.matches()
    }

    /**
     * 判斷參數是否為負整數
     * @param input 待判斷的字串
     * @return 是否為負整數
     */
    fun isNegativeInteger(input: String): Boolean {
        val p = Pattern.compile("^-[1-9]\\d*")
        val m = p.matcher(input)
        return m.matches()
    }

    /**
     * 判斷參數是否為浮點數
     * @param input 待判斷的字串
     * @return 是否為浮點數
     */
    fun isFloat(input: String): Boolean {
        val p = Pattern.compile("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+")
        val m = p.matcher(input)
        return m.matches()
    }

    /**
     * 判斷參數是否為正浮點數
     * @param input 待判斷的字串
     * @return 是否為正浮點數
     */
    fun isPositiveFloat(input: String): Boolean {
        val p = Pattern.compile("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*")
        val m = p.matcher(input)
        return m.matches()
    }

    /**
     * 判斷參數是否為負浮點數
     * @param input 待判斷的字串
     * @return 是否為負浮點數
     */
    fun isNegativeFloat(input: String): Boolean {
        val p = Pattern.compile("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*")
        val m = p.matcher(input)
        return m.matches()
    }

    /**
     * 取得系統語言。
     * @return 系統語言字串，例如：繁體中文 = zh-TW
     */
    val systemLanguage: String
        get() {
            val str = Locale.getDefault().language
            val str2 = Locale.getDefault().country
            return "$str-$str2"
        }
}