package com.ray650128.mybasemodule.extensions

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ray650128.mybasemodule.base.SafeClickListener
import com.ray650128.mybasemodule.util.PermissionUtil


fun ComponentActivity.checkPermissions(permissions: Array<String>,
                                       @NonNull permissionResultCallback: PermissionUtil.PermissionResultCallback) {
    PermissionUtil.checkPermission(this, permissions, permissionResultCallback)
}

fun ComponentActivity.requestPermissionsResult(requestCode: Int,
                                                 @NonNull permissions: Array<out String>,
                                                 @NonNull grantResults: IntArray) {
    PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
}

fun Fragment.checkPermissions(permissions: Array<String>,
                              @NonNull permissionResultCallback: PermissionUtil.PermissionResultCallback) {
    PermissionUtil.checkPermission(requireActivity(), permissions, permissionResultCallback)
}

fun Fragment.requestPermissionsResult(requestCode: Int,
                                               @NonNull permissions: Array<out String>,
                                               @NonNull grantResults: IntArray) {
    PermissionUtil.onRequestPermissionsResult(requireActivity(), requestCode, permissions, grantResults)
}

//region gotoActivity
/**
 * 跳轉到其他 Activity
 * @param activity  目標 Activity
 * @param bundle    欲傳入的參數
 * @param closeSelf 是否將自己關閉
 */
fun <A> AppCompatActivity.gotoActivity(activity: Class<A>, bundle: Bundle? = null, closeSelf: Boolean = false) {
    gotoActivity(this, activity, bundle, closeSelf)
}

/**
 * 跳轉到其他 Activity
 * @param activity  目標 Activity
 * @param bundle    欲傳入的參數
 * @param closeSelf 是否將自己關閉
 */
fun <A> Fragment.gotoActivity(activity: Class<A>, bundle: Bundle? = null, closeSelf: Boolean = false) {
    gotoActivity(requireActivity(), activity, bundle, closeSelf)
}

private fun <A> gotoActivity(from: Activity, to: Class<A>, bundle: Bundle? = null, closeSelf: Boolean = false) {
    val intent = Intent(from, to)
    if (bundle != null) intent.putExtras(bundle)
    from.startActivity(intent)
    if (closeSelf) {
        from.finish()
    }
}
//endregion

//region gotoActivityAndClearStack
/**
 * 跳轉到其他 Activity，並將所有的 Activity 從任務堆疊中移除
 * @param activity  目標 Activity
 * @param bundle    欲傳入的參數
 */
fun <A> AppCompatActivity.gotoActivityAndClearStack(activity: Class<A>, bundle: Bundle? = null) {
    gotoActivityAndClearStack(this, activity, bundle)
}

/**
 * 跳轉到其他 Activity，並將所有的 Activity 從任務堆疊中移除
 * @param activity  目標 Activity
 * @param bundle    欲傳入的參數
 */
fun <A> Fragment.gotoActivityAndClearStack(activity: Class<A>, bundle: Bundle? = null) {
    gotoActivityAndClearStack(requireActivity(), activity, bundle)
}

private fun <A> gotoActivityAndClearStack(from: Activity, to: Class<A>, bundle: Bundle? = null) {
    val intent = Intent(from, to)
    if (bundle != null) intent.putExtras(bundle)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_TASK_ON_HOME
    from.startActivity(intent)
}
//endregion

//region openViewer
/**
 * 開啟系統預覽程式
 * @param uri       URI
 * @param closeSelf 是否將自己關閉
 */
fun AppCompatActivity.openViewer(uri: Uri, closeSelf: Boolean = false) {
    openViewer(this, uri, closeSelf)
}

/**
 * 開啟系統預覽程式
 * @param uri       URI
 * @param closeSelf 是否將自己關閉
 */
fun Fragment.openViewer(uri: Uri, closeSelf: Boolean = false) {
    openViewer(requireActivity(), uri, closeSelf)
}

private fun openViewer(activity: Activity, uri: Uri, closeSelf: Boolean) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = uri
    activity.startActivity(intent)
    if (closeSelf) {
        activity.finish()
    }
}
//endregion

//region showMessageDialog
/**
 * 顯示訊息對話框
 * @param msg  字串
 * @param onPositivePress  按下確定時的動作，預設 null=按下後關閉
 * @param onNegativePress  按下取消時的動作，預設 null=不顯示
 */
fun AppCompatActivity.showMessageDialog(
    msg: String,
    onPositivePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null,
    onNegativePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null
) {
    showMessageDialog(this, msg, onPositivePress, onNegativePress)
}

/**
 * 顯示訊息對話框
 * @param resId  字串資源
 * @param onPositivePress  按下確定時的動作，預設 null=按下後關閉
 * @param onNegativePress  按下取消時的動作，預設 null=不顯示
 */
fun AppCompatActivity.showMessageDialog(
    @StringRes resId: Int,
    onPositivePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null,
    onNegativePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null
) {
    showMessageDialog(this, resId, onPositivePress, onNegativePress)
}

/**
 * 顯示訊息對話框
 * @param msg  字串
 * @param onPositivePress  按下確定時的動作，預設 null=按下後關閉
 * @param onNegativePress  按下取消時的動作，預設 null=不顯示
 */
fun Fragment.showMessageDialog(
    msg: String,
    onPositivePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null,
    onNegativePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null
) {
    showMessageDialog(requireContext(), msg, onPositivePress, onNegativePress)
}

/**
 * 顯示訊息對話框
 * @param resId  字串資源
 * @param onPositivePress  按下確定時的動作，預設 null=按下後關閉
 * @param onNegativePress  按下取消時的動作，預設 null=不顯示
 */
fun Fragment.showMessageDialog(
    @StringRes resId: Int,
    onPositivePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null,
    onNegativePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null
) {
    showMessageDialog(requireContext(), resId, onPositivePress, onNegativePress)
}

private fun showMessageDialog(
    context: Context,
    msg: String,
    onPositivePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null,
    onNegativePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null
) {
    val dialog = AlertDialog.Builder(context)
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

private fun showMessageDialog(
    context: Context,
    @StringRes resId: Int,
    onPositivePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null,
    onNegativePress: ((dlg: DialogInterface, which: Int) -> Unit)? = null
) {
    val dialog = AlertDialog.Builder(context)
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
//endregion

//region hideKeyboard
/**
 * 隱藏虛擬鍵盤
 * @param view
 */
fun AppCompatActivity.hideKeyboard(view: View) {
    hideKeyboard(this, view)
}

/**
 * 隱藏虛擬鍵盤
 * @param view
 */
fun Fragment.hideKeyboard(view: View) {
    hideKeyboard(requireContext(), view)
}

private fun hideKeyboard(context:Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
//endregion

//region showKeyboard
/**
 * 顯示虛擬鍵盤
 * @param view  焦點 View
 */
fun AppCompatActivity.showKeyboard(view: EditText?) {
    showKeyboard(this, view)
}

/**
 * 顯示虛擬鍵盤
 * @param view  焦點 View
 */
fun Fragment.showKeyboard(view: EditText?) {
    showKeyboard(requireContext(), view)
}

private fun showKeyboard(context: Context, view: EditText?) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view?.requestFocus()
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    view?.isCursorVisible = true
}
//endregion

//region showSelectDialog
/**
 * 顯示單選對話框
 * @param title         標題，可有可無
 * @param list          清單內容
 * @param onItemClick   清單點選事件回傳
 */
fun AppCompatActivity.showSelectDialog(title: String?, list: List<String>, onItemClick: (Int) -> Unit) {
    showSelectDialog(this, title, list, onItemClick)
}

/**
 * 顯示單選對話框
 * @param title         標題，可有可無
 * @param list          清單內容
 * @param onItemClick   清單點選事件回傳
 */
fun Fragment.showSelectDialog(title: String?, list: List<String>, onItemClick: (Int) -> Unit) {
    showSelectDialog(requireContext(), title, list, onItemClick)
}

private fun showSelectDialog(context: Context, title: String?, list: List<String>, onItemClick: ((Int) -> Unit)) {
    val builder = AlertDialog.Builder(context)
    if (title != null) {
        builder.setTitle(title)
    }
    builder.setItems(list.toTypedArray()) { _, which ->
        onItemClick.invoke(which)
    }
    builder.setCancelable(true)
    builder.create().show()
}
//endregion

/**
 * 簡易版顯示 Dialog Fragment
 * tag 參數改為取得他自己的類別名稱
 * @param fragmentManager   Fragment 管理器
 */
fun DialogFragment.show(fragmentManager: FragmentManager) {
    show(fragmentManager, this.javaClass.simpleName)
}
