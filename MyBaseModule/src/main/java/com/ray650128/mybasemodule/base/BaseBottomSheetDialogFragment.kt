package com.ray650128.mybasemodule.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ray650128.mybasemodule.R
import com.ray650128.mybasemodule.util.DensityUtil
import java.lang.reflect.ParameterizedType


/**
 * 從底部彈出的普通列表對話框
 */
abstract class BaseBottomSheetDialogFragment<T : ViewBinding> : BottomSheetDialogFragment() {

    private lateinit var mContext: Context

    lateinit var binding: T

    private var mDialogBehavior: BottomSheetBehavior<View>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val d = super.onCreateDialog(savedInstanceState)
        d.setOnShowListener {
            // 停用視窗外側的觸控事件
            d.window?.findViewById<View>(R.id.touch_outside)?.setOnClickListener(null)
            // 防止拖動
            val bottomSheet = d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.isDraggable = false
            }
        }
        return d
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        binding = method.invoke(null, layoutInflater, container, false) as T
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = dialog as BottomSheetDialog?
        val bottomSheet = dialog?.delegate?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = DensityUtil(mContext).getScreenHeight()
            mDialogBehavior = BottomSheetBehavior.from(bottomSheet)
            mDialogBehavior?.skipCollapsed = true
            mDialogBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    fun showKeyboard(view: EditText?) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        view?.isCursorVisible = true
    }

    fun hideKeyboard(view: View) {
        val imm: InputMethodManager =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showMessageDialog(
        message: String,
        buttonEvent: ((dialog: DialogInterface) -> Unit)? = null
    ) {
        val dialog = AlertDialog.Builder(mContext)
        dialog.setMessage(message)
        dialog.setPositiveButton(android.R.string.ok) { thisDialog, _ ->
            buttonEvent?.invoke(thisDialog)
            thisDialog.cancel()
        }
        dialog.show()
    }

    fun showMessageDialog(resId: Int, buttonEvent: ((dialog: DialogInterface) -> Unit)? = null) {
        val dialog = AlertDialog.Builder(mContext)
        dialog.setMessage(resId)
        dialog.setPositiveButton(android.R.string.ok) { thisDialog, _ ->
            buttonEvent?.invoke(thisDialog)
            thisDialog.cancel()
        }
        dialog.show()
    }
}