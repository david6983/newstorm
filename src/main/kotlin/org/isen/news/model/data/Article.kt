package org.isen.news.model.data

import java.util.*

data class Article(
        val source: Source?,
        val author: String?,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: Date,
        val content: String?
) {
}