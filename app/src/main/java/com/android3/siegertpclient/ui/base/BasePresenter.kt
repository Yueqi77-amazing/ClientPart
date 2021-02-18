package com.android3.siegertpclient.ui.base

import android.content.Context

open class BasePresenter<V : BaseView> {

    var view: V? = null

    fun onAttach(view: V) {
        this.view = view
    }

    fun onDetach() {
        view = null
    }
}