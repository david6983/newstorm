package org.isen.news.model.impl

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.INewsSourceModel
import org.isen.news.model.data.SourceRequest
import kotlin.properties.Delegates

class SourceModel : DefaultNewsModel(), INewsSourceModel {
    companion object : Logging {
        const val SOURCE_ROOT = "sources"
    }

    var sourceRequest: SourceRequest by Delegates.observable(SourceRequest()) {
        property, oldValue, newValue ->
        TopHeadlineModel.logger.info("source request property change, notify observer")
        for (listener in listeners) {
            listener.updateNews(newValue)
        }
    }

    override fun findSources(category: String?, language: String?, country: String?, page: Int?) {
        logger.info("get sources")
        GlobalScope.launch { getSources(category, language, country, page) }
    }

    private suspend fun getSources(category: String?, language: String?, country: String?, page: Int?) {
        buildSourceQuery(category, language, country, page)
                .httpGet().responseObject(SourceRequest.Deserializer()) {
                    request, response, result ->
                    logger.info("StatusCode:${response.statusCode}")
                    result.component1()?.let {
                        sourceRequest = it
                    }
                }
    }

    fun buildSourceQuery(category: String?, language: String?, country: String?, page: Int?): String {
        var query: String = "$SOURCE_ROOT?apiKey=$apiKey"
        if (category != null) {
            query += "&category=$category"
        }
        if (language != null) {
            query += "&language=$language"
        }
        if (country != null) {
            query += "&country=$country"
        }
        if (page != null && page > 1) {
            query += "&page=$page"
        }
        if (pageSize != 0) {
            query += "&pageSize=$pageSize"
        }
        logger.info("Source querry : $query")
        return query
    }
}