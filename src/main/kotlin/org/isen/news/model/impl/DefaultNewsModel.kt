package org.isen.news.model.impl

import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.INewsModel
import org.isen.news.model.INewsModelObservable
import tornadofx.*

class DefaultNewsModel : ViewModel(), INewsModel {
    companion object : Logging

    private var listeners: ArrayList<INewsModelObservable> = ArrayList()


    override fun register(listener: INewsModelObservable) {
        listeners.add(listener)
    }

    override fun unregister(listener: INewsModelObservable) {
        listeners.remove(listener)
    }


}