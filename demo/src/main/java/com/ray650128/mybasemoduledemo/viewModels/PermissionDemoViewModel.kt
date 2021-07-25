package com.ray650128.mybasemoduledemo.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ray650128.mybasemoduledemo.model.Song
import com.ray650128.mybasemoduledemo.repository.SongRepository

class PermissionDemoViewModel : ViewModel() {

    fun getSongList(context: Context): MutableLiveData<ArrayList<Song>> {
        return SongRepository.getPlayList(context)
    }
}