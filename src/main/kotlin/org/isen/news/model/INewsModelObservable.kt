package org.isen.news.model

interface INewsModelObservable {
    fun updateNews(data: Any)
    fun updateStatusCode(code: Int)
}