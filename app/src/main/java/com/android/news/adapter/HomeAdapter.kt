package com.android.news.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.android.news.R
import com.android.news.model.NewsModel
import com.android.news.util.convertDpToPx
import java.util.*

class HomeAdapter(context: Context, val list: List<NewsModel>): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var layoutInflater: LayoutInflater
    private var listener: OnItemClickListener? = null

    companion object {
        val ITEM_FIRST = 0
        val ITEM_ANY = 1
    }

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    fun setListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        var view = layoutInflater.inflate(R.layout.row_news_sources, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0){
            return ITEM_FIRST
        }else{
            return ITEM_ANY
        }
    }
    override fun getItemCount() = list.count()

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        if (holder.itemViewType == ITEM_FIRST){
            holder.addMarginForFirstRow()
        }
        val model = list.get(position)
        holder.newsTitle.text = model!!.title
        holder.newBody.text = model!!.description
        holder.container.setOnClickListener {
            listener?.onClick(position)
        }
        holder.randomColor(position)
    }

    class HomeViewHolder(val view: View): RecyclerView.ViewHolder(view){

        var container: View
        var newsTitle: TextView
        var newBody: TextView
        var cardView: View
        var colorView: View

        init {
            newsTitle = view.findViewById(R.id.titleView)
            newBody = view.findViewById(R.id.descriptionView)
            container = view.findViewById(R.id.parentView)
            cardView = view.findViewById(R.id.cardView)
            colorView = view.findViewById(R.id.colorView)
        }

        fun addMarginForFirstRow(){
            var layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)

            layoutParams.setMargins(view.convertDpToPx(10), view.convertDpToPx(80), view.convertDpToPx(10), view.convertDpToPx(2))
            cardView.layoutParams = layoutParams
        }

        fun randomColor(position: Int){
            val arrColor = listOf(
                    R.color.colorPrimary,
                    R.color.colorAccent,
                    R.color.colorPrimaryDark,
                    R.color.card_color,
                    R.color.md_yellow_300,
                    R.color.soft_red)

            if (position <= arrColor.size - 1){
                var gd = colorView.background as GradientDrawable
                gd.setColor(view.context.resources.getColor(arrColor[position]))
                colorView.background = gd
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }
}

