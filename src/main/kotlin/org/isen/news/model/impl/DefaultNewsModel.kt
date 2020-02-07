package org.isen.news.model.impl

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuse.core.CacheBuilder
import com.github.kittinunf.fuse.core.StringDataConvertible
import com.github.kittinunf.fuse.core.build
import com.github.kittinunf.fuse.core.scenario.ExpirableCache
import com.github.kittinunf.fuse.core.scenario.get
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.INewsModel
import org.isen.news.model.INewsModelObservable
import org.isen.news.util.NEWS_API_KEY
import org.isen.news.util.NEWS_API_ROOT
import tornadofx.*
import kotlin.properties.Delegates

open class DefaultNewsModel : ViewModel(), INewsModel {
    companion object : Logging

    protected var listeners: ArrayList<INewsModelObservable> = ArrayList()

    private val tempDir = createTempDir().absolutePath

    init {
        FuelManager.instance.basePath = NEWS_API_ROOT
    }

    var pageSize: Int by Delegates.observable(0) {
        property, oldValue, newValue ->
        TopHeadlineModel.logger.info("page size property change, notify observer")
        for (listener in listeners) {
            listener.updateNews(newValue)
        }
    }

    var apiKey: String by Delegates.observable(NEWS_API_KEY) {
        property, oldValue, newValue ->
        TopHeadlineModel.logger.info("api key property change, notify observer")
        for (listener in listeners) {
            listener.updateNews(newValue)
        }
    }

    override fun register(listener: INewsModelObservable) {
        listeners.add(listener)
    }

    override fun unregister(listener: INewsModelObservable) {
        listeners.remove(listener)
    }
}