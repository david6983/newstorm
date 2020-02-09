package org.isen.news.model.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/**
 * Represents a country
 *
 * @property name
 * @property alpha2Code
 * @property flag
 */
data class Country(
        val name: String,
        val alpha2Code: String,
        val flag: String
) {
    constructor(): this("", "", "")

    class Deserializer : ResponseDeserializable<Array<Country>> {
        override fun deserialize(content: String): Array<Country>? =
                Gson().fromJson(content, Array<Country>::class.java)
    }

    override fun toString(): String {
        return name
    }
}