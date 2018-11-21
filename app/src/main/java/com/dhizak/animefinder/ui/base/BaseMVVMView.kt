package com.dhizak.animefinder.ui.base

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.dhizak.animefinder.ui.states.intents.BaseViewIntent
import com.dhizak.animefinder.ui.states.states.BaseViewState
import com.dhizak.animefinder.ui.viewmodel.BaseViewModel
import org.koin.android.ext.android.get

interface BaseMVVMView<V : BaseViewState, T : BaseViewIntent> {

    fun initViewModel(): BaseViewModel<T, V>

    fun getViewModel(): Class<BaseViewModel<T, V>>

    fun render(viewState: V)

    fun postToModel(viewAction: T)
}
