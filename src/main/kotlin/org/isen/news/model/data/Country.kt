package org.isen.news.model.data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Country(
        val name: String,
        val alpha2Code: String,
        val flag: String
) {
    constructor(): this("", "", "")

    class Deserializer : ResponseDeserializable<Array<Country>> {
        override fun deserialize(content: String): Array<Country> =
                Gson().fromJson(content, Array<Country>::class.java)
    }
}