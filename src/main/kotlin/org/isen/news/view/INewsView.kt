package org.isen.news.view

import org.isen.news.model.INewsModelObservable

interface INewsView : INewsModelObservable {
    fun display()
    fun close()
}