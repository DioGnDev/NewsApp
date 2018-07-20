package com.android.news.view.iface

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseView: MvpView{
    fun showProgress()
    fun hideProgress()
}