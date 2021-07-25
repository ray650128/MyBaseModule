package com.ray650128.mybasemoduledemo

import android.Manifest
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ray650128.mybasemodule.base.BaseActivity
import com.ray650128.mybasemodule.util.PermissionUtil
import com.ray650128.mybasemodule.viewModelUtils.ViewModelField
import com.ray650128.mybasemodule.viewModelUtils.bindViewModel
import com.ray650128.mybasemoduledemo.adapter.SongListAdapter
import com.ray650128.mybasemoduledemo.databinding.ActivityPermissionDemoBinding
import com.ray650128.mybasemoduledemo.model.Song
import com.ray650128.mybasemoduledemo.viewModels.PermissionDemoViewModel

class PermissionDemoActivity : BaseActivity<ActivityPermissionDemoBinding>() {

    companion object {
        private val PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    @ViewModelField("PermissionDemo")
    lateinit var viewModel: PermissionDemoViewModel

    private lateinit var adapter: SongListAdapter
    private var songList: ArrayList<Song> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()

        initRecyclerView()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        PermissionUtil.checkPermission(
            this,
            PERMISSIONS,
            object : PermissionUtil.PermissionResultCallback {
                override fun onGrant() {
                    viewModel.getSongList(this@PermissionDemoActivity)
                        .observe(this@PermissionDemoActivity, {
                            if (it != null) {
                                songList = it
                                adapter.updateList(songList)
                            }
                        })
                }

                override fun onDeny(denies: ArrayList<String>?) {}
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtil.onRequestPermissionsResult(
            this, requestCode,
            permissions, grantResults
        )
    }

    private fun initRecyclerView() {
        adapter = SongListAdapter(songList).apply {
            onItemClick = { position ->
                showMessageDialog("你點選了\n${songList[position].artist} 的 ${songList[position].title}")
            }
        }
        binding.recyclerView3.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView3.adapter = adapter
    }
}