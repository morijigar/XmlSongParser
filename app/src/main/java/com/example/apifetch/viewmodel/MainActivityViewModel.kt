package com.example.apifetch.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apifetch.base.BaseActivity
import com.example.apifetch.model.Entry
import com.example.apifetch.model.FeedXml
import com.example.apifetch.model.MoviesList
import com.example.myapplication.databse.Database
import com.example.myapplication.ws.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivityViewModel : ViewModel() {


    private val repository: Repository = Repository()

    var listData = MutableLiveData<MutableList<Entry>>()
    var progressVisibility:MutableLiveData<Int> = MutableLiveData(View.GONE)

    fun callTopSongs() {
        viewModelScope.launch {
            progressVisibility.postValue(View.VISIBLE)
            val responseBody = repository.getTopSongs()
            val xmlResponse: String = responseBody.string()
            Log.e("xmlResponse", xmlResponse);
            progressVisibility.postValue(View.GONE)
        }

    }

    fun callTop20Songs(context: Context) {
        viewModelScope.launch {
            try {
                progressVisibility.postValue(View.VISIBLE)
                val feedXml = repository.getTop20Songs()
                //Log.e("xmlResponse", feedXml.title);

                Database.getInstance(context).appDatabase.feedsDao().save(feedXml.entries)
                listData.postValue(feedXml.entries)
                /* val dddd =  Database.getInstance(context).appDatabase.feedsDao().getAllData()
                  Log.e("xmlResponse", feedXml.title);

                  dddd.forEach {
                      Log.e("xmlResponse", it.link.href);
                  }*/
                progressVisibility.postValue(View.GONE)
            } catch (throwable: Throwable) {
                progressVisibility.postValue(View.GONE)
                onApiError(throwable)
            }


        }

    }

    fun getDataFromLocal(context: Context){
        viewModelScope.launch {
            val dataList = Database.getInstance(context).appDatabase.feedsDao().getAllData()
            listData.postValue(dataList)
        }
    }

    fun getDataForRecycleView(baseActivity: BaseActivity<*,*>){
        if (baseActivity.isOnline()) {
            callTop20Songs(baseActivity)
        }else{
            getDataFromLocal(baseActivity)
        }
    }

    fun onApiError(throwable: Throwable) {
        when (throwable) {
            is IOException -> Log.e("TAG", "IO Exception")
            is HttpException -> {
                val code = throwable.code()
                //val errorResponse = convertErrorBody(throwable)
                Log.e("TAG", "HttpException $code")
            }
            else -> {
                Log.e("TAG", "throwable " + throwable.message)
            }
        }
    }


}