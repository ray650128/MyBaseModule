package com.ray650128.mybasemoduledemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ray650128.mybasemoduledemo.databinding.ItemDemoListBinding
import com.ray650128.mybasemoduledemo.model.Student

class DemoListAdapter(data: ArrayList<Student>) : RecyclerView.Adapter<DemoListAdapter.ViewHolder>() {

    private var mData: ArrayList<Student> = data

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemDemoListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
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

    inner class ViewHolder(private val binding: ItemDemoListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(student: Student) {
            binding.textStudentName.text = student.name
            binding.textStudentId.text = "${student.id}"
        }
    }
}
