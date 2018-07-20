package com.android.news.view.iface

import com.android.news.model.ArticlesModel
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ArticleView: BaseView {
    fun showList(arrayList: List<ArticlesModel>)
    fun showUpdatedList(list: List<ArticlesModel>)
    fun updatePage()
}