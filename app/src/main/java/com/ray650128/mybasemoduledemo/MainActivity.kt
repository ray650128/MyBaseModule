package com.ray650128.mybasemoduledemo

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import com.ray650128.mybasemoduledemo.databinding.ActivityMainBinding
import com.ray650128.mybasemodule.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val useCustomActionBar: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 這裡展示了功能表左側按鈕的生成方式。
         * 如同 ActionBar 一樣，
         * 使用 setHomeAsUpIndicator 設定 icon
         * 使用 setDisplayHomeAsUpEnabled 顯示左側按鈕
         */
        supportActionBar?.let {
            it.setHomeAsUpIndicator(R.drawable.ic_emoji)
            it.setDisplayHomeAsUpEnabled(true)
        }

        /**
         * 這裡展示了 showMessageDialog() 的使用方式。
         */
        binding.btnShowMessage.setOnClickListener {
            showMessageDialog("這是測試訊息")
        }

        /**
         * 這裡展示了 showMessageDialog()，並自訂確定按鈕的事件。
         */
        binding.btnShowMessage2.setOnClickListener {
            showMessageDialog("這是測試訊息",
                { _, _ ->
                    showMessageDialog("你按下確定按鈕")
                }
            )
        }

        /**
         * 這裡展示了 showMessageDialog()，並自訂確定/取消按鈕的事件。
         */
        binding.btnShowMessage3.setOnClickListener {
            showMessageDialog("這是測試訊息",
                { _, _ ->
                    showMessageDialog("你按下確定按鈕")
                },
                { _, _ ->
                    showMessageDialog("你按下取消按鈕")
                }
            )
        }

        /**
         * 這裡展示了 showLoadingDialog()，並在五秒後呼叫 hideLoadingDialog() 關閉。
         */
        binding.btnShowLoading.setOnClickListener {
            showLoadingDialog()
            Handler(Looper.getMainLooper()).postDelayed({
                hideLoadingDialog()
            }, 5000)
        }

        binding.btnRecyclerViewDemo.setOnClickListener {
            gotoActivity(ListDemoActivity::class.java)
        }

        binding.btnPermissionDemo.setOnClickListener {
            gotoActivity(PermissionDemoActivity::class.java)
        }

        setToolbarTextColor(Color.WHITE)
    }

    /**
     * 這裡展示了功能表的生成方式。
     * 如同 ActionBar 一樣，使用 menuInflater 將 menu 資源引用
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 這裡展示了功能表的的事件處理方式。
     * 如同 ActionBar 一樣，根據 itemId 處理事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}