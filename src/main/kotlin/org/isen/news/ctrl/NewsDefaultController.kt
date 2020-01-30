package org.isen.news.ctrl

import org.isen.news.model.INewsModel
import org.isen.news.view.INewsView

open class NewsDefaultController() {
    protected val views: ArrayList<INewsView> = ArrayList()

    protected var models: ArrayList<INewsModel> = arrayListOf()

    constructor(models: ArrayList<INewsModel>) : this() {
        this.models = models
    }

    fun registerView(v: INewsView) {
        this.views.add(v)
        models.forEach {
            it.register(v)
        }
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