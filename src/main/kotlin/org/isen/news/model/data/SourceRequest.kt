package org.isen.news.model.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/**
 * Response object for a source request
 *
 * @property status
 * @property sources
 */
data class SourceRequest(
        val status: String,
        val sources: List<Source>?
) {
    constructor(): this("", null)

    class Deserializer : ResponseDeserializable<SourceRequest> {
        override fun deserialize(content: String): SourceRequest =
                Gson().fromJson(content, SourceRequest::class.java)
    }
}