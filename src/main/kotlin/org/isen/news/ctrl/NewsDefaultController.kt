package org.isen.news.ctrl

import org.isen.news.model.INewsModel
import org.isen.news.view.INewsView

class NewsDefaultController(var model: INewsModel) {
    private var views: ArrayList<INewsView> = ArrayList()

    fun registerView(v: INewsView) {
        this.views.add(v)
        this.model.register(v)
    }

    fun displayView() {
        views.forEach {
            it.display()
        }
    }

    fun closeView() {
        views.forEach {
            it.close()
        }
    }
}