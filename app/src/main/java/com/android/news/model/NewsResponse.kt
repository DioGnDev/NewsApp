package com.android.news.model

data class NewsResponse(val sources: List<SourcesItem>?,
                        val status: String = "")


data class SourcesItem(val country: String = "",
                       val name: String = "",
                       val description: String = "",
                       val language: String = "",
                       val id: String = "",
                       val category: String = "",
                       val url: String = "")


