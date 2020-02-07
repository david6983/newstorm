package org.isen.news.ctrl

import org.isen.news.view.INewsView

interface INewsController {
    fun registerView(v: INewsView)
    fun displayView()
    fun closeView()
}
