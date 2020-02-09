package org.isen.news.model

import org.isen.news.model.data.SortBy
import java.util.*

interface INewsEverythingModel {
    fun findArticles(
            q: String?,
            qInTitle: String?,
            sources: String?,
            domains: String?,
            excludeDomains: String?,
            from: Date?,
            to: Date?,
            languages: String?,
            sortBy: SortBy,
            page: Int?
    )
}