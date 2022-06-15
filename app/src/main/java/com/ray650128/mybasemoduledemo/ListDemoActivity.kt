package com.ray650128.mybasemoduledemo

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ray650128.mybasemodule.base.BaseActivity
import com.ray650128.mybasemodule.extensions.gotoActivity
import com.ray650128.mybasemodule.viewModelUtils.ViewModelField
import com.ray650128.mybasemodule.viewModelUtils.bindViewModel
import com.ray650128.mybasemoduledemo.adapter.DemoListAdapter
import com.ray650128.mybasemoduledemo.databinding.ActivityListDemoBinding
import com.ray650128.mybasemoduledemo.model.Student
import com.ray650128.mybasemoduledemo.viewModels.ListDemoViewModel


class ListDemoActivity : BaseActivity<ActivityListDemoBinding>() {

    override val useCustomActionBar: Boolean = false

    @ViewModelField("Students")
    lateinit var viewModel: ListDemoViewModel

    private lateinit var adapter: DemoListAdapter
    private var studentList: ArrayList<Student> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bindViewModel()

        initRecyclerView()

        showLoadingDialog()

        viewModel.loadStudents().observe(this) { students ->
            if (students != null) {
                studentList = students
                adapter.updateList(studentList)
            }
            hideLoadingDialog()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initRecyclerView() {
        adapter = DemoListAdapter(studentList).apply {
            onItemClick = { position ->
                val bundle = Bundle()
                bundle.putInt("ID", studentList[position].id)
                gotoActivity(StudentActivity::class.java, bundle)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.adapter = adapter
    }
}