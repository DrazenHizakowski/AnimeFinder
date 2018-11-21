package com.dhizak.animefinder.ui.states.states

import android.support.annotation.StringRes

open class BaseViewState {
    data class ShowError(val errorMessage: String? = null, @StringRes val errorResource: Int = -1) : BaseViewState()
    open inner class ShowMessage(val message: String? = null, @StringRes val resource: Int = -1) : BaseViewState()
    open class ShowProgress(val isLoading: Boolean) : BaseViewState()
    open class Exit() : BaseViewState()
}