package com.ray650128.mybasemoduledemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ray650128.mybasemoduledemo.R
import com.ray650128.mybasemoduledemo.model.Student

class DemoListAdapter(data: ArrayList<Student>) : RecyclerView.Adapter<DemoListAdapter.ViewHolder>() {

    private var mData: ArrayList<Student> = data

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_demo_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DemoListAdapter.ViewHolder, position: Int) {
        holder.bindData(mData[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    override fun getItemCount(): Int = mData.size

    fun updateList(updateList: ArrayList<Student>) {
        mData = updateList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var studentName: TextView = view.findViewById(R.id.textStudentName)
        private var studentId: TextView = view.findViewById(R.id.textStudentId)

        fun bindData(student: Student) {
            studentName.text = student.name
            studentId.text = "${student.id}"
        }
    }
}
