package org.isen.news.model.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/**
 * Response object for everything request
 *
 * @property status
 * @property totalResults
 * @property articles
 */
data class EverythingRequest(
        val status: String,
        val totalResults: Int,
        val articles: List<Article>?
) {
    constructor(): this(
    "",
    0,
    null
    )

    class Deserializer : ResponseDeserializable<EverythingRequest> {
        override fun deserialize(content: String): EverythingRequest =
                Gson().fromJson(content, EverythingRequest::class.java)
    }
}