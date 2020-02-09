package org.isen.news.model.impl

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.INewsEverythingModel
import org.isen.news.model.data.EverythingQuery
import org.isen.news.model.data.EverythingRequest
import org.isen.news.model.data.HeadlineRequest
import org.isen.news.model.data.SortBy
import java.util.*
import kotlin.properties.Delegates

class EverythingModel : DefaultNewsModel(), INewsEverythingModel {
    companion object : Logging {
        const val EVERYTHING_ROOT = "everything"
    }

    var everythingRequest: EverythingRequest by Delegates.observable(EverythingRequest()) {
        property, oldValue, newValue ->
        logger.info("everything property change, notify " +
                "observer")
        for (listener in listeners) {
            listener.updateNews(newValue)
        }
    }

    override fun findArticles(query: EverythingQuery) {
        logger.info("get articles from everything")
        GlobalScope.launch { getArticles(query) }
    }

    private suspend fun getArticles(query: EverythingQuery) {
        buildEverythingQuery(query).httpGet().responseObject(EverythingRequest
                .Deserializer()) {
            request, response, result ->
            logger.info("StatusCode (breaking news):${response.statusCode}")
            errorCode = response.statusCode
            result.component1()?.let {
                everythingRequest = it
            }
        }
    }

    fun buildEverythingQuery(query: EverythingQuery) : String {
        var q = "$EVERYTHING_ROOT?apiKey=$apiKey"

        return q
    }
}