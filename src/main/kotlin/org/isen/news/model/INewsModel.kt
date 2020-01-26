package org.isen.news.model

interface INewsModel {
    fun register(listener: INewsModelObservable)
    fun unregister(listener: INewsModelObservable)

    // API
}