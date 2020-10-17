package com.example.apifetch.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apifetch.BR
import com.example.apifetch.R
import com.example.apifetch.adapter.PostAdapter
import com.example.apifetch.adapter.SongListAdapter
import com.example.apifetch.base.BaseActivity
import com.example.apifetch.databinding.ActivityMainBinding
import com.example.apifetch.model.Entry
import com.example.apifetch.model.Result
import com.example.apifetch.viewmodel.MainActivityViewModel
import com.example.myapplication.databse.Database
import kotlinx.coroutines.launch


class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main
    override val viewModel: MainActivityViewModel
        get() = ViewModelProvider(this).get(MainActivityViewModel::class.java)

    private var listData: MutableList<Entry> = mutableListOf()
    var adapter: SongListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val layoutManager = GridLayoutManager(this,2)
        viewDataBinding?.recycler?.layoutManager = layoutManager
        viewDataBinding?.recycler?.setHasFixedSize(true)

        viewDataBinding!!.swipeRefresh.setOnRefreshListener {
            viewModel.getDataForRecycleView(this)
        }


        setObserver()
        viewModel.getDataForRecycleView(this)
    }

    private fun setObserver() {

        viewModel.listData.observe(this, Observer {
            viewDataBinding?.swipeRefresh?.isRefreshing = false

            if (null == adapter) {
                listData.clear()
                listData.addAll(it)
                adapter = SongListAdapter(this, listData)
                viewDataBinding?.recycler?.adapter = adapter
            } else {
                //adapter?.updateList(it)
                listData.addAll(it)
                adapter?.notifyDataSetChanged()
            }

        })
    }


}
