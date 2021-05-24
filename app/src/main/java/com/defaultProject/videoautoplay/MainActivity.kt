package com.defaultProject.videoautoplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.defaultProject.videoautoplay.databinding.ActivityMainBinding
import com.defaultProject.videoautoplay.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_VideoAutoPlay)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mBinding.viewModel = mViewModel


        //Main Logic is Here to Play & Pause the Video
        mViewModel.setPlayPause(mBinding.recyclerView)

    }
}