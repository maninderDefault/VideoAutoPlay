package com.defaultProject.videoautoplay.adapter

import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

var playPosition = -1
var videoSurfaceDefaultHeight = 0
var screenDefaultHeight = 0
private const val TAG = "recyclerView"
private const val NowPlaying = "Now Playing.."
private const val Paused = "Paused"
private const val Upnext = "Up next.."


class PlayRecyclerView {

    fun playVideo(recyclerView: RecyclerView) {

        recyclerView.apply {
            var context = context.applicationContext
            val display =
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val point = Point()
            display.getSize(point)
            videoSurfaceDefaultHeight = point.x
            screenDefaultHeight = point.y


            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 ) {
                        Log.d(TAG, "onScrolled: UP or DOWN")

                        if (!recyclerView.canScrollVertically(1)) {
                            findAndPlay(recyclerView, true, 0)
                        } else {
                            findAndPlay(recyclerView, false, 0)
                        }
                    }else if(dy<0){


                        findAndPlay(recyclerView, false,1)

                    }
                }
            })
        }
    }
}


private fun findAndPlay(recyclerView: RecyclerView, reachedEnd: Boolean, paused: Int) {

    var targetPosition = 0
    var playPosition = -1
    recyclerView.apply {
        if (!reachedEnd) {
            val lastPosition =
                (layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

            pauseVideo(lastPosition, recyclerView,paused)

            val startPosition: Int =
                (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            Log.d(TAG, "findAndPlay: start position $startPosition")
            var endPosition: Int =
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            Log.d(TAG, "findAndPlay: end position $endPosition")


            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1
            }


            // if error comes then return
            if (startPosition < 0 || endPosition < 0) {
                return
            }


            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition) {
                val startPositionVideoHeight: Int = getVisibleVideoSurfaceHeight(
                    startPosition,
                    recyclerView
                )
                Log.d(TAG, "startPositionVideoHeight $startPositionVideoHeight")
                val endPositionVideoHeight: Int = getVisibleVideoSurfaceHeight(
                    endPosition,
                    recyclerView
                )
                Log.d(TAG, "endPositionVideoHeight $endPositionVideoHeight")

                targetPosition =
                    if (startPositionVideoHeight > endPositionVideoHeight) {
                        startPosition

                    } else {
                        endPosition

                    }

                //Scenario 1: To pause the one already playing and less screen then other
                if (targetPosition == endPosition) {
                    pauseVideo(startPosition, recyclerView,paused)
                } else {
                    pauseVideo(endPosition, recyclerView,paused)
                }

            } else {
                targetPosition = startPosition
            }
        } else {
            //Scenario 2 : Here Play that last video and pause other views
            targetPosition =
                (layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
            val firstVisiblePosition =
                (layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
            for (i in firstVisiblePosition until targetPosition)
                pauseVideo(i, recyclerView,paused)
        }


        //Scenario 3: if video is already playing -> return
        if (targetPosition == playPosition) {
            return
        }

        // setting back the PlayPosition
        playPosition = targetPosition

        Log.d(TAG, "target position : $targetPosition ")
        val currentPosition =
            targetPosition - (layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        Log.d(TAG, "current position $currentPosition")
        //VERY IMPORTANT PART TO GET REFERENCE --CHECK AGAIN TODoo
        val child = getChildAt(currentPosition) ?: return
        Log.d(TAG, "child $child")
        val holder = child.tag as? PlayViewHolder
        Log.d(TAG, "holder $holder")
        if (holder == null) {
            playPosition = -1
            return
        }
        var VideoText = holder.binding.tvVideoText
        var ivPause = holder.binding.ivPause
        var ivPlay = holder.binding.ivPlays

        VideoText.text = NowPlaying
        ivPause.visibility = View.GONE
        ivPlay.visibility = View.VISIBLE

    }
}

fun pauseVideo(position: Int, recyclerView: RecyclerView,paused:Int) {
    recyclerView.apply {
        val currentPosition =
            position - (layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        val child = getChildAt(currentPosition) ?: return
        val holder = child.tag as? PlayViewHolder

        if (holder == null) {
            playPosition = -1
            return
        }
        var VideoText = holder.binding.tvVideoText
        var ivPause = holder.binding.ivPause
        var ivPlay = holder.binding.ivPlays
        if (paused==0){
        if (VideoText.text == NowPlaying) {
            VideoText.text = Paused
        } else if (VideoText.text == "Up Next..") {
            VideoText.text = "Up Next.."
        } } else if (paused==1){
            if (VideoText.text == "Now Playing..") {
                VideoText.text = "Paused"
            } else if (VideoText.text == "Up Next..") {
                VideoText.text = "Paused"
            }




        }
        ivPause.visibility = View.VISIBLE
        ivPlay.visibility = View.GONE
    }
}

private fun getVisibleVideoSurfaceHeight(
    playPosition: Int,
    recyclerview: RecyclerView
): Int {
    recyclerview.apply {
        val at: Int =
            playPosition - (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()


        val child: View = getChildAt(at) ?: return 0

        val location = IntArray(2)
        child.getLocationInWindow(location)

        return if (location[1] < 0) {
            location[1] + videoSurfaceDefaultHeight
        } else {
            screenDefaultHeight - location[1]
        }
    }
}
