package com.android.news.presenter

import android.util.Log
import com.android.news.model.ArticlesModel
import com.android.news.network.ServiceGenerator
import com.android.news.view.iface.ArticleView
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticlePresenter: MvpBasePresenter<ArticleView>() {

    private lateinit var disposable: Disposable
    private var articles = ArrayList<ArticlesModel>()

    fun loadArticle(source: String, initialPage: Int){
        view?.showProgress()
        articles.clear()
        disposable = ServiceGenerator.createService().getArticles(source, "en", initialPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onNext ->
                    view?.hideProgress()
                    if (onNext.isSuccessful && onNext.code() == 200){
                        onNext.body()!!.articles?.map {
                            articles.add(ArticlesModel(it.urlToImage, it.title, it.description, it.url))
                        }
                        view?.showList(articles)
                        view?.updatePage()
                    }
                }, { onError ->
                    view?.hideProgress()
                    Log.d("request", onError.message)
                }, {
                    view?.hideProgress()
                    Log.d("request", "completed")
                })
    }

    fun loadMoreArticle(source: String, currentPage: Int){
        view?.showProgress()
        disposable = ServiceGenerator.createService().getArticles(source, "en", currentPage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onNext ->
                    view?.hideProgress()
                    //load data
                    if (onNext.isSuccessful && onNext.code() == 200){
                        onNext.body()!!.articles?.map {
                            articles.add(ArticlesModel(it.urlToImage, it.title, it.description, it.url))
                        }
                        view?.showUpdatedList(articles)
                        view?.updatePage()
                    }
                }, { onError ->
                    view?.hideProgress()
                    Log.d("request", onError.message)
                }, {
                    view?.hideProgress()
                    Log.d("request", "completed")
                })
    }

}