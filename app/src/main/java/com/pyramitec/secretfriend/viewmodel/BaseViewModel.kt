package com.pyramitec.secretfriend.viewmodel

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), Observable {

//    @Bindable
//    var errorMessage: String = ""
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.errorMessage)
//        }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    protected fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

//    fun showError(message: String?) {
//        this.errorMessage = message ?: "Erro desconhecido"
//    }
}