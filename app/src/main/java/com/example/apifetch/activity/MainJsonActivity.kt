package com.example.apifetch.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.apifetch.BR
import com.example.apifetch.R
import com.example.apifetch.base.BaseActivity
import com.example.apifetch.databinding.ActivityMainJsonBinding
import com.example.apifetch.fragment.MainFragment
import com.example.apifetch.viewmodel.MainActivityViewModel


class MainJsonActivity : BaseActivity<ActivityMainJsonBinding, MainActivityViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main_json
    override val viewModel: MainActivityViewModel
        get() = ViewModelProvider(this).get(MainActivityViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentReplaceBackPress(true, MainFragment.newInstance("", ""))

    }


}
