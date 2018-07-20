package com.android.news.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.android.news.R
import com.android.news.model.ArticlesModel
import com.bumptech.glide.Glide

class ArticleAdapter(val context: Context, val list: List<ArticlesModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var layoutInflater: LayoutInflater
    private var listener: ArticleAdapter.OnItemClickListener ? = null
    var isLoadMore = false

    companion object {
        val ITEM_VIEW_TYPE_CONTENT = 1
        val ITEM_VIEW_TYPE_LOADING = 2
    }

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    fun setListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        when (viewType){
            ITEM_VIEW_TYPE_CONTENT -> {
                view = layoutInflater.inflate(R.layout.row_article, parent, false)
                return ArticleViewHolder(view)
            }
            else -> {
                view = layoutInflater.inflate(R.layout.item_loading, parent, false)
                return LoadingViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int{
        if (list == null) {
            return 0
        }else{
            return list.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list.get(position) == null){
            return ITEM_VIEW_TYPE_LOADING
        }else{
            return ITEM_VIEW_TYPE_CONTENT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder){
            val article = list[position]
            Glide.with(context).load(article?.imageUrl).into(holder.imageView)
            holder.title.text = article?.title
            holder.description.text = article?.description

            holder.parentView.setOnClickListener {
                listener?.apply {
                    itemClick(article.linkUrl!!)
                }
            }

        }else {
            var loadingVH = holder as LoadingViewHolder
            loadingVH.progressView.isIndeterminate = true
        }
    }

    interface OnItemClickListener {
        fun itemClick(linkUrl: String)
    }
}

class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view){

    var parentView: View
    var imageView: ImageView
    var title: TextView
    var description: TextView

    init {
        parentView = view.findViewById(R.id.parentView)
        imageView = view.findViewById(R.id.imageView)
        title = view.findViewById(R.id.titleTextView)
        description = view.findViewById(R.id.detailTextView)
    }
}

class LoadingViewHolder(view: View): RecyclerView.ViewHolder(view){
    var progressView: ProgressBar

    init {
        progressView = view.findViewById(R.id.progressLoading)
    }
}