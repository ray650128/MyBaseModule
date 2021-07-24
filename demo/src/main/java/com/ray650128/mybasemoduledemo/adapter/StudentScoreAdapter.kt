package com.ray650128.mybasemoduledemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ray650128.mybasemoduledemo.R
import com.ray650128.mybasemoduledemo.databinding.ItemExamListBinding
import com.ray650128.mybasemoduledemo.model.Exam
import com.ray650128.mybasemoduledemo.model.Student

class StudentScoreAdapter(data: ArrayList<Exam>) : RecyclerView.Adapter<StudentScoreAdapter.ViewHolder>() {

    private var mData: ArrayList<Exam> = data

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentScoreAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemExamListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentScoreAdapter.ViewHolder, position: Int) {
        holder.bindData(mData[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    override fun getItemCount(): Int = mData.size

    fun updateList(updateList: ArrayList<Exam>) {
        mData = updateList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemExamListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(exam: Exam) {
            binding.textSubject.text = exam.subjectName
            binding.textScore.text = "${exam.score}"
        }
    }
}
