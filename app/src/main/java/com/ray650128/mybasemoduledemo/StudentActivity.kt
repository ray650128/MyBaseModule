package com.ray650128.mybasemoduledemo

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ray650128.mybasemodule.base.BaseActivity
import com.ray650128.mybasemodule.viewModelUtils.ViewModelField
import com.ray650128.mybasemodule.viewModelUtils.bindViewModel
import com.ray650128.mybasemoduledemo.adapter.DemoListAdapter
import com.ray650128.mybasemoduledemo.adapter.StudentScoreAdapter
import com.ray650128.mybasemoduledemo.databinding.ActivityStudentBinding
import com.ray650128.mybasemoduledemo.model.Exam
import com.ray650128.mybasemoduledemo.model.Student
import com.ray650128.mybasemoduledemo.viewModels.ListDemoViewModel

class StudentActivity : BaseActivity<ActivityStudentBinding>() {

    @ViewModelField("Students")
    lateinit var viewModel: ListDemoViewModel

    private lateinit var adapter: StudentScoreAdapter
    private var examList: ArrayList<Exam> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        val studentId = bundle?.getInt("ID")

        bindViewModel()

        initRecyclerView()

        showLoadingDialog()

        if (studentId != null)
        viewModel.getStudent(studentId).observe(this, { student ->
            if (student != null) {
                binding.textStudentName.text = student.name
                examList = student.exams
                adapter.updateList(examList)

                viewModel.getAverage().observe(this, { average ->
                    if (average != null) {
                        binding.textAverage.text = String.format("%.0f", average)
                    }
                    hideLoadingDialog()
                })
            }
            hideLoadingDialog()
        })
    }

    private fun initRecyclerView() {
        adapter = StudentScoreAdapter(examList)
        binding.recyclerView2.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView2.adapter = adapter
    }
}