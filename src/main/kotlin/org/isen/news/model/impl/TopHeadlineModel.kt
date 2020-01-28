package org.isen.news.model.impl

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.INewsTopHeadlineModel
import org.isen.news.model.data.HeadlineRequest
import kotlin.properties.Delegates


class TopHeadlineModel : DefaultNewsModel(), INewsTopHeadlineModel {
    companion object : Logging {
        const val TOP_HEADLINE_ROOT = "top-headlines"
    }

    var headlineRequest: HeadlineRequest by Delegates.observable(HeadlineRequest()) {
        property, oldValue, newValue ->
        logger.info("top headline property change, notify observer")
        for (listener in listeners) {
            listener.updateNews(newValue)
        }
    }

    override fun findBreakingNews(country: String?, category: String?, page: Int?, q: String?) {
        logger.info("get breaking news")
        GlobalScope.launch { getBreakingNews(country, category, page, q) }
    }

    override fun findBreakingNewsBySource(source: String, page: Int?, q: String?) {
        logger.info("get breaking news")
        GlobalScope.launch { getBreakingNewsBySource(source, page, q) }
    }

    private suspend fun getBreakingNews(country: String?, category: String?, page: Int?, q: String?) {
        buildBreakingNewsQuery(country, category, page, q)
                .httpGet().responseObject(HeadlineRequest.Deserializer()) {
                    request, response, result ->
                    logger.info("StatusCode:${response.statusCode}")
                    result.component1()?.let {
                        headlineRequest = it
                    }
                }
    }

    private suspend fun getBreakingNewsBySource(source: String, page: Int?, q: String?) {
        buildBreakingNewsBySourceQuery(source, page, q)
                .httpGet().responseObject(HeadlineRequest.Deserializer()) {
                    request, response, result ->
                    logger.info("StatusCode:${response.statusCode}")
                    result.component1()?.let {
                        headlineRequest = it
                    }
                }
    }

    fun buildBreakingNewsQuery(country: String?, category: String?, page: Int?, q: String?) : String {
        var query = "$TOP_HEADLINE_ROOT?apiKey=$apiKey"
        if (country != null) {
            query += "&country=$country"
        }
        if (category != null) {
            query += "&category=$category"
        }
        if (q != null) {
            query += "&q=$q"
        }
        if (page != null && page > 1) {
            query += "&page=$page"
        }
        if (pageSize != 0) {
            query += "&pageSize=$pageSize"
        }
        return query
    }

    fun buildBreakingNewsBySourceQuery(source: String, page: Int?, q: String?) : String {
        var query = "$TOP_HEADLINE_ROOT?apiKey=$apiKey&sources=$source"
        if (q != null) {
            query += "&q=$q"
        }
        if (page != null && page > 1) {
            query += "&page=$page"
        }
        if (pageSize != 0) {
            query += "&pageSize=$pageSize"
        }
        return query
    }
}