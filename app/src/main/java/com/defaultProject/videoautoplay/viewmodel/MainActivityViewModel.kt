package com.defaultProject.videoautoplay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.defaultProject.videoautoplay.R
import com.defaultProject.videoautoplay.adapter.PlayAdapter
import com.defaultProject.videoautoplay.adapter.PlayRecyclerView
import com.defaultProject.videoautoplay.model.DataSource
import com.defaultProject.videoautoplay.model.VideoData

class MainActivityViewModel:ViewModel() {
    var data:ArrayList<DataSource> = ArrayList()
    var adapter: PlayAdapter

    init {
        data= VideoData().createData()
        adapter= PlayAdapter(R.layout.recycler_view_layout,data,this)

    }

    fun setPlayPause(recyclerView:RecyclerView){
        PlayRecyclerView().playVideo(recyclerView )
    }

    fun setAdapter(): PlayAdapter {
        return adapter
    }

    fun setTitle(position:Int):String{
        return data[position].title
    }
    fun setVideotext(position:Int):String{return data[position].Videotext}
    fun setPlayImage(position:Int):Int{return data[position].playImage}
    fun setPauseImage(position:Int):Int{return data[position].pauseImage}

}