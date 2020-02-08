package org.isen.news.ctrl

interface INewsControllerParams {
    fun changeApiKey(key: String)
    fun changePageSize(pageSize: Int)
    fun findErrorCode(): Int
}