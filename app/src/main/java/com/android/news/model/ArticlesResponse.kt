package com.android.news.model

data class ArticlesItem(val publishedAt: String = "",
                        val author: String = "",
                        val urlToImage: String = "",
                        val description: String = "",
                        val source: Source,
                        val title: String = "",
                        val url: String = "")


data class ArticlesResponse(val totalResults: Int = 0,
                            val articles: List<ArticlesItem>?,
                            val status: String = "")


data class Source(val name: String = "",
                  val id: String = "")


