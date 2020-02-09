package org.isen.news.model.data

import java.util.*

/**
 * Represents an article
 *
 * @property source
 * @property author
 * @property title
 * @property description
 * @property url
 * @property urlToImage
 * @property publishedAt
 * @property content
 */
data class Article(
        val source: Source?,
        val author: String?,
        val title: String,
        val description: String?,
        val url: String,
        val urlToImage: String,
        val publishedAt: Date,
        val content: String?
) {
    constructor() : this(
            null,
            null,
            "",
            null,
            "",
            "",
            Date(),
            null
    )
}