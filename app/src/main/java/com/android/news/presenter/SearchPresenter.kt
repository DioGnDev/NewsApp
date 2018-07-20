package com.android.news.presenter

import android.util.Log
import com.android.news.model.ArticlesModel
import com.android.news.network.ServiceGenerator
import com.android.news.view.iface.SearchView
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter: MvpBasePresenter<SearchView>() {

    private lateinit var disposable: Disposable
    private var articles = ArrayList<ArticlesModel>()

    fun loadArticle(source: String, initialPage: Int, searchText: String){
        view?.showProgress()
        articles.clear()
        disposable = ServiceGenerator.createService().searchArticles(source, "en", initialPage, searchText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onNext ->
                    view?.hideProgress()
                    if (onNext.isSuccessful && onNext.code() == 200){
//                        Log.d("request", "${onNext.body()!!.articles!!}")
                        if (onNext.body()!!.articles!!.size <= 0){
                            view?.showEmptyText("")
                        }else{
                            onNext.body()!!.articles?.map {
                                articles.add(ArticlesModel(it.urlToImage, it.title, it.description, it.url))
                            }
                            view?.showList(articles)
                            view?.updatePage()
                        }
                    }
                }, { onError ->
                    view?.hideProgress()
                    Log.d("request", onError.message)
                }, {
                    view?.hideProgress()
                    Log.d("request", "completed")
                })
    }

    fun loadMoreArticle(source: String, currentPage: Int, searchText: String){
        disposable = ServiceGenerator.createService().searchArticles(source, "en", currentPage, searchText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onNext ->
                    if (onNext.isSuccessful && onNext.code() == 200){
//                        Log.d("request", "${onNext.body()!!.articles!!}")
                        if (onNext.body()!!.articles!!.size <= 0){
                            view?.showEmptyText("")
                        }else{
                            onNext.body()!!.articles?.map {
                                articles.add(ArticlesModel(it.urlToImage, it.title, it.description, it.url))
                            }
                            view?.showUpdatedList(articles)
                            view?.updatePage()
                        }
                    }
                }, { onError ->
                    Log.d("request", onError.message)
                }, {
                    Log.d("request", "completed")
                })
    }
}