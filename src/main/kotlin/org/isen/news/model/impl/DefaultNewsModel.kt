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

/**
 * Default model for every models
 * support of tornadofx with ViewModel
 */
open class DefaultNewsModel : ViewModel(), INewsModel {
    /**
     * logger
     */
    companion object : Logging

    /**
     * listenners
     */
    protected var listeners: ArrayList<INewsModelObservable> = ArrayList()

    init {
        // default api base path
        FuelManager.instance.basePath = NEWS_API_ROOT
    }

    /**
     * number of article by page
     */
    var pageSize: Int by Delegates.observable(0) {
        property, oldValue, newValue ->
        logger.info("page size changed from $oldValue to $newValue")
    }

    /**
     * store the api key
     */
    var apiKey: String by Delegates.observable(NEWS_API_KEY) {
        property, oldValue, newValue ->
        logger.info("api key changed from $oldValue to $newValue")
    }

    var errorCode: Int by Delegates.observable(200) {
        property, oldValue, newValue ->
        logger.info("error code change, notify observer")
        for (listener in listeners) {
            listener.updateStatusCode(newValue)
        }
    }

    override fun register(listener: INewsModelObservable) {
        listeners.add(listener)
    }

    override fun unregister(listener: INewsModelObservable) {
        listeners.remove(listener)
    }
}