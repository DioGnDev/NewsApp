package com.android.news.view.impl

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.android.news.R
import com.android.news.adapter.ArticleAdapter
import com.android.news.model.ArticlesModel
import com.android.news.presenter.SearchPresenter
import com.android.news.util.EndlessRecyclerViewScrollListener
import com.android.news.view.iface.SearchView
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment: MvpFragment<SearchView, SearchPresenter>(), SearchView {

    private lateinit var adapter: ArticleAdapter
    private var viewManager: LinearLayoutManager
    private var source = ""
    private var searchText = ""
    private var currentPage = 1
    private var imm: InputMethodManager? = null

    companion object {

        @JvmStatic
        fun newInstance(source: String): SearchFragment{
            val searchFragment = SearchFragment()
            val args = Bundle()
            args.putString("source", source)
            searchFragment.arguments = args
            return searchFragment
        }
    }

    init {
        viewManager = LinearLayoutManager(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imm = activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        arguments?.also {
            source = it.getString("source")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_search, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_toolbar.setOnClickListener {  }

        loadingView.setOnClickListener {  }

        mylist.setHasFixedSize(true)
        mylist.layoutManager = viewManager

        edSearch.requestFocus()
        edSearch.imeOptions = EditorInfo.IME_ACTION_SEARCH
        edSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (!edSearch.text.trim().isEmpty()){
                        currentPage = 1
                        searchText = edSearch.text.toString()
                        presenter.loadArticle(source, currentPage, searchText)

                        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    return true
                }
                return false
            }
        })

        navBack.setOnClickListener {
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
            fragmentManager?.run {
                popBackStack()
            }
        }
    }

    override fun createPresenter() = SearchPresenter()

    override fun showList(list: List<ArticlesModel>) {
        mylist.visibility = View.VISIBLE
        adapter = ArticleAdapter(activity!!.baseContext, list)
        mylist.adapter = adapter
        adapter.notifyDataSetChanged()

        mylist.addOnScrollListener(object : EndlessRecyclerViewScrollListener(viewManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                Log.d("load more info", "${page} ${totalItemsCount}")
                if (page == currentPage){
                    presenter.loadMoreArticle(source, currentPage, searchText)
                }else{
                    Log.d("state", "don't request in the same page")
                }
            }
        })
    }

    override fun showEmptyText(text: String) {
        emptyText.visibility = View.VISIBLE
        mylist.visibility = View.GONE
    }

    override fun showUpdatedList(list: List<ArticlesModel>) {
        mylist.visibility = View.VISIBLE
        adapter.notifyDataSetChanged()
    }

    override fun updatePage() {
        currentPage = currentPage + 1
    }

    override fun showProgress() {
        loadingView.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        loadingView.visibility = View.GONE
    }
}