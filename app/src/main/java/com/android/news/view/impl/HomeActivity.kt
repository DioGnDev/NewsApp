package com.android.news.view.impl

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.news.R
import com.android.news.adapter.HomeAdapter
import com.android.news.model.NewsModel
import com.android.news.presenter.HomePresenter
import com.android.news.view.iface.HomeView
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : MvpActivity<HomeView, HomePresenter>(), HomeView {

    private lateinit var adapter: HomeAdapter
    private var viewManager: RecyclerView.LayoutManager

    init {
        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun createPresenter() = HomePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mylist.setHasFixedSize(true)
        mylist.layoutManager = viewManager

        presenter.getNews()
    }

    override fun showProgress() {
        progressLoading.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressLoading.visibility = View.GONE
    }

    override fun showList(list: List<NewsModel>) {
        adapter = HomeAdapter(this, list)
        mylist.adapter = adapter
        adapter.setListener(object : HomeAdapter.OnItemClickListener{
            override fun onClick(position: Int) {
                list[position]?.also {
                    startActivity(Intent(this@HomeActivity, ArticleActivity::class.java)?.apply {
                        putExtra("source", it.id)
                        putExtra("title", it.title)
                    })
                }
            }
        })
        adapter.notifyDataSetChanged()
    }
}
