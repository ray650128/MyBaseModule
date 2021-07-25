package com.ray650128.mybasemoduledemo.repository

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.ray650128.mybasemoduledemo.model.Song

object SongRepository {

    private var playlist = ArrayList<Song>()
    private val playListLiveData: MutableLiveData<ArrayList<Song>> by lazy {
        MutableLiveData<ArrayList<Song>>()
    }

    fun getPlayList(context: Context): MutableLiveData<ArrayList<Song>> {
        playlist.clear()

        //retrieve song info
        val musicResolver = context.contentResolver
        val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC
        val sortOrder = MediaStore.Audio.Media.DISPLAY_NAME

        // 經由ContentProvider來取得外部儲存媒體上的音樂檔案的情報
        val musicCursor = musicResolver.query(musicUri, null, selection, null, sortOrder)

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            val idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val totalTimeColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            //add songs to list
            do {
                val thisId = musicCursor.getLong(idColumn)
                val thisTitle = musicCursor.getString(titleColumn)
                val thisArtist = musicCursor.getString(artistColumn)
                val thisTime = musicCursor.getInt(totalTimeColumn)
                val song = Song(thisId, thisTitle, thisArtist, thisTime)

                playlist.add(song)
            } while (musicCursor.moveToNext())
        }

        musicCursor?.close()

        // 排列由A~Z
        playlist.sortWith { a, b ->
            a.title.compareTo(b.title)
        }

        playListLiveData.value = playlist

        return playListLiveData
    }
}