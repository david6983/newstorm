package org.isen.news.model.data

import java.util.*

data class EverythingQuery(
    val q: String?,
    val qInTitle: String?,
    val sources: String?,
    val domains: String?,
    val excludeDomains: String?,
    val from: Date?,
    val to: Date?,
    val languages: String?,
    val sortBy: SortBy,
    val page: Int?
) {
}