package org.isen.news.model.impl

import com.github.kittinunf.fuel.httpGet
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

    override fun findArticles(
            q: String?,
            qInTitle: String?,
            sources: String?,
            domains: String?,
            excludeDomains: String?,
            from: Date?,
            to: Date?,
            languages: String?,
            sortBy: SortBy,
            page: Int?
    ) {

    }

    private suspend fun getArticles(
            q: String?,
            qInTitle: String?,
            sources: String?,
            domains: String?,
            excludeDomains: String?,
            from: Date?,
            to: Date?,
            languages: String?,
            sortBy: SortBy,
            page: Int?
    ) {
        buildEverythingQuery(
            EverythingQuery(
                    q,
                    qInTitle,
                    sources,
                    domains,
                    excludeDomains,
                    from,
                    to,
                    languages,
                    sortBy,
                    page
            )
        ).httpGet().responseObject(EverythingRequest.Deserializer()) {
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