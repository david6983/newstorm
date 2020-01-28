package org.isen.news.model

interface INewsTopHeadlineModel {
    fun findBreakingNews(country: String?, category: String?, page: Int?, q: String?)
    fun findBreakingNewsBySource(source: String, page: Int?, q: String?)
}