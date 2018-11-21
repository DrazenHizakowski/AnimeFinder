package com.dhizak.animefinder.ui.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.dhizak.animefinder.ui.states.intents.BaseViewIntent
import com.dhizak.animefinder.ui.states.states.BaseViewState
import com.dhizak.animefinder.ui.viewmodel.BaseViewModel

abstract class BaseMVVMFragment<V : BaseViewState, T : BaseViewIntent> : Fragment(), BaseMVVMView<V, T> {

    private lateinit var viewModel: BaseViewModel<T, V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
        viewModel.liveData.observe(this, Observer {
            render(it!!)
        })
    }

    override fun initViewModel(): BaseViewModel<T, V> {
        return ViewModelProviders.of(this).get(getViewModel())
    }

    override fun postToModel(viewAction: T) {
        viewModel.postToModel(viewAction)
    }
}