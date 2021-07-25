package com.ray650128.mybasemoduledemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ray650128.mybasemoduledemo.databinding.ItemDemoListBinding
import com.ray650128.mybasemoduledemo.databinding.ItemSongListBinding
import com.ray650128.mybasemoduledemo.model.Song
import com.ray650128.mybasemoduledemo.model.Student

class SongListAdapter(data: ArrayList<Song>) : RecyclerView.Adapter<SongListAdapter.ViewHolder>() {

    private var mData: ArrayList<Song> = data

    var onItemClick: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemSongListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongListAdapter.ViewHolder, position: Int) {
        holder.bindData(mData[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    override fun getItemCount(): Int = mData.size

    fun updateList(updateList: ArrayList<Song>) {
        mData = updateList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemSongListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(song: Song) {
            binding.textSongTitle.text = song.title
            binding.textSongArtist.text = song.artist
            binding.textSongTime.text = song.timeString
        }
    }
}
