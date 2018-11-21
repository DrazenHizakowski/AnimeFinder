package com.dhizak.animefinder.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.bumptech.glide.Glide.init
import com.dhizak.animefinder.ui.states.ViewIntentObservable
import com.dhizak.animefinder.ui.states.intents.BaseViewIntent
import com.dhizak.animefinder.ui.states.states.BaseViewState
import java.util.*

abstract class BaseViewModel<V : BaseViewIntent, T : BaseViewState> : ViewModel(), Observer {

    public val liveData: MutableLiveData<T> = MutableLiveData()
    private val receiver = ViewIntentObservable<V>()

    init {
        receiver.addObserver(this)
    }

    override fun update(o: Observable?, arg: Any?) {
        gotFromView(arg as V)
    }

    protected abstract fun gotFromView(viewAction: V)

    fun postToModel(action: V) {
        receiver.viewIntent = action
    }

    fun postToView(state: T) {
        liveData.value = state
    }

    override fun onCleared() {
        super.onCleared()
        receiver.deleteObserver(this)
    }

}