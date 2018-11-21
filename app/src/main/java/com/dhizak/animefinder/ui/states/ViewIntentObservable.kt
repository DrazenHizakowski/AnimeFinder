package com.dhizak.animefinder.ui.states

import android.util.Log
import com.dhizak.animefinder.ui.states.intents.BaseViewIntent
import java.util.*

class ViewIntentObservable<V : BaseViewIntent> : Observable() {

    var viewIntent: V? = null
        set(value) {
            value
            setChanged()
            notifyObservers(value)
        }

}