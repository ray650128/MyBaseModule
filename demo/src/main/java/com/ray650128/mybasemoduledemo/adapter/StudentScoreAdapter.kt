package com.ray650128.mybasemoduledemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ray650128.mybasemoduledemo.R
import com.ray650128.mybasemoduledemo.model.Exam
import com.ray650128.mybasemoduledemo.model.Student

class StudentScoreAdapter(data: ArrayList<Exam>) : RecyclerView.Adapter<StudentScoreAdapter.ViewHolder>() {

    private var mData: ArrayList<Exam> = data

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentScoreAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_exam_list, parent, false)
        return ViewHolder(view)
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

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var subjectName: TextView = view.findViewById(R.id.textSubject)
        private var score: TextView = view.findViewById(R.id.textScore)

        fun bindData(exam: Exam) {
            subjectName.text = exam.subjectName
            score.text = "${exam.score}"
        }
    }
}
