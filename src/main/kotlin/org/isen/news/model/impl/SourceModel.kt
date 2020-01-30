package org.isen.news.model.impl

import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.INewsSourceModel
import org.isen.news.model.data.SourceRequest
import kotlin.properties.Delegates

class SourceModel : DefaultNewsModel(), INewsSourceModel {
    companion object : Logging {
        const val SOURCE_ROOT = "source"
    }

    var sourceRequest: SourceRequest by Delegates.observable(SourceRequest()) {
        property, oldValue, newValue ->
        TopHeadlineModel.logger.info("source request property change, notify observer")
        for (listener in listeners) {
            listener.updateNews(newValue)
        }
    }

    override fun findSources(category: String?, language: String?, country: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}