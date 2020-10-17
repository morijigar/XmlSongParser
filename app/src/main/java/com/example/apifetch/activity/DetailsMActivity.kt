package com.example.apifetch.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apifetch.BR
import com.example.apifetch.R
import com.example.apifetch.base.BaseActivity
import com.example.apifetch.databinding.ActivityDetailBinding
import com.example.apifetch.databinding.ActivityDetailMBinding
import com.example.apifetch.viewmodel.DetailActivityViewModel
import com.example.apifetch.viewmodel.DetailMActivityViewModel


class DetailsMActivity : BaseActivity<ActivityDetailMBinding, DetailMActivityViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_detail_m
    override val viewModel: DetailMActivityViewModel
        get() = ViewModelProvider(this).get(DetailMActivityViewModel::class.java)

    lateinit var movieId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = intent.getStringExtra("movieId")
        viewModel.callMovieDetail(movieId)

        showData()


    }

    private fun showData() {

        viewModel.listData.observe(this, Observer {
            //viewDataBinding!!.progress.visibility = View.GONE
            viewDataBinding!!.model = it!!


        })


    }


}
