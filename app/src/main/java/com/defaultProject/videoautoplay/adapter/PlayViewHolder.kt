package com.defaultProject.videoautoplay.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.defaultProject.videoautoplay.databinding.RecyclerViewLayoutBinding
import com.defaultProject.videoautoplay.viewmodel.MainActivityViewModel

class PlayViewHolder(var binding: RecyclerViewLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var parent: View = itemView

    fun bind(viewModel: MainActivityViewModel, position: Int) {
        parent.tag = this
    }
}