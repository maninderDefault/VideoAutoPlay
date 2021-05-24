package com.defaultProject.videoautoplay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.defaultProject.videoautoplay.databinding.RecyclerViewLayoutBinding
import com.defaultProject.videoautoplay.viewmodel.MainActivityViewModel
import com.defaultProject.videoautoplay.model.DataSource

class PlayAdapter(
    @LayoutRes var layoutId: Int,
    var data: ArrayList<DataSource>,
    var viewModel: MainActivityViewModel
) : RecyclerView.Adapter<PlayViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<RecyclerViewLayoutBinding>(layoutInflater, viewType, parent, false)
        return PlayViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PlayViewHolder, position: Int) {
        holder.binding.viewModel = viewModel
        holder.binding.position =position
        holder.parent = holder.itemView
        holder.bind(viewModel,position)
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }

    override fun getItemCount(): Int = data.size

}