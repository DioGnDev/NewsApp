package com.android.news.view.iface

import com.android.news.model.NewsModel
import com.hannesdorfmann.mosby3.mvp.MvpView

interface HomeView: BaseView {
    fun showList(list: List<NewsModel>)
}