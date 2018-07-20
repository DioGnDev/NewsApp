package com.android.news.presenter

import android.util.Log
import com.android.news.model.NewsModel
import com.android.news.network.ServiceGenerator
import com.android.news.view.iface.HomeView
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomePresenter: MvpBasePresenter<HomeView>() {

    lateinit var disposable: Disposable
    private var newsList = ArrayList<NewsModel>()

    fun getNews(){
        view?.showProgress()
        newsList.clear()
        disposable = ServiceGenerator.createService().getNews("en")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onNext ->
                    view?.hideProgress()
                    if (onNext.isSuccessful && onNext.code() == 200){
//                        Log.d("state", onNext.body().toString())
                        onNext.body()?.sources?.map {
                            newsList.add(NewsModel(it.id, it.name, it.description))
                        }
                        view?.showList(newsList)
                    }
                }, { onError ->
                    view?.hideProgress()
                    Log.d("state", onError.message)
                }, {
                    view?.hideProgress()
                    Log.d("state", "completed")
                })
    }
}