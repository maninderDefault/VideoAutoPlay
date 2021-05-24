package com.defaultProject.videoautoplay.model

import com.defaultProject.videoautoplay.R

class VideoData {

    fun createData(): ArrayList<DataSource> {
        var list = arrayListOf<DataSource>()

        for (i in 1..10) {
            list.add(DataSource("Video $i", R.drawable.ic_pause, R.drawable.ic_play, "Up Next"))
        }
        return list
    }
}