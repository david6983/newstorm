package org.isen.news.ctrl.jfx

import javafx.beans.property.SimpleListProperty
import javafx.collections.ObservableList
import org.isen.news.ctrl.INewsController
import org.isen.news.model.INewsModel
import org.isen.news.view.INewsView

import tornadofx.*

open class NewsDefaultFxController : Controller(), INewsController {
    protected val viewsProperty = SimpleListProperty<INewsView>()
    protected var views: ObservableList<INewsView> by viewsProperty

    protected val modelsProperty = SimpleListProperty<INewsModel>()
    protected var models: ObservableList<INewsModel> by modelsProperty

    init {
        views = ArrayList<INewsView>().asObservable()
        models = ArrayList<INewsModel>().asObservable()
    }

    override fun registerView(v: INewsView) {
        this.views.add(v)
        models.forEach {
            it.register(v)
        }
    }

    override fun displayView() {
        views.forEach {
            it.display()
        }
    }

    override fun closeView() {
        views.forEach {
            it.close()
        }
    }
}