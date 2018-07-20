package com.android.news.view.impl

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.news.R
import com.android.news.adapter.ArticleAdapter
import com.android.news.model.ArticlesModel
import com.android.news.presenter.ArticlePresenter
import com.android.news.util.EndlessRecyclerViewScrollListener
import com.android.news.view.iface.ArticleView
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity: MvpActivity<ArticleView, ArticlePresenter>(), ArticleView, ArticleAdapter.OnItemClickListener {

    private lateinit var adapter: ArticleAdapter
    private var viewManager: LinearLayoutManager
    private var source = ""
    private var title = ""
    private var currentPage = 1

    init {
        viewManager = LinearLayoutManager(this)
    }

    override fun createPresenter() = ArticlePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        intent.extras?.apply {
            source = getString("source")
            title = getString("title")
            presenter.loadArticle(source, currentPage)
        }

        mylist.setHasFixedSize(true)
        mylist.layoutManager = viewManager

        sourceText.text = title

        searchView.setOnClickListener {
            loadFragment(SearchFragment.newInstance(source))
        }

        navBack.setOnClickListener { finish() }

        progressLoading.setOnClickListener {  }
    }

    override fun showList(list: List<ArticlesModel>) {
        adapter = ArticleAdapter(this, list)
        mylist.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setListener(this)

        mylist.addOnScrollListener(object : EndlessRecyclerViewScrollListener(viewManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                //loading data
                if (page == currentPage){
                    presenter.loadMoreArticle(source, currentPage)
                }else{
                    Log.d("state", "don't request in the same page")
                }
            }
        })
    }

    override fun itemClick(linkUrl: String) {
        Log.d("state","urlnya adalah ${linkUrl}")
        loadFragment(WebViewFragment.newInstance(linkUrl))
    }

    override fun showUpdatedList(list: List<ArticlesModel>) {
        adapter.notifyDataSetChanged()
    }

    override fun updatePage() {
        currentPage = currentPage + 1
        Log.d("state", "page ${currentPage}")
    }

    override fun showProgress() {
        progressLoading.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressLoading.visibility = View.GONE
    }

    //private function
    private fun loadFragment(fragment: Fragment){
        var ft = supportFragmentManager.beginTransaction()?.apply {
            replace(R.id.search_content, fragment)
            addToBackStack(null)
        }
        ft?.commit()
    }
}