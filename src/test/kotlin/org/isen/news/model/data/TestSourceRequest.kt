package org.isen.news.model.data

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.isen.news.util.NEWS_API_KEY
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestSourceRequest {
    @Before
    fun init() {
        FuelManager.instance.basePath = "https://newsapi.org/v2/"
    }

    @Test
    fun allSources() {
        val (request, response, result) = "sources?apiKey=$NEWS_API_KEY"
                .httpGet().responseObject(SourceRequest.Deserializer())

        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.sources.also {
                assertNotNull(it)
            } ?.let {
                assertEquals(129, it.size)
            }
        }
    }

    @Test
    fun allSourcesFrance() {

    }

    @Test
    fun allSourcesFranceAndEntertainment() {

    }

    @Test
    fun sourcesFranceAndEnglish() {

    }

    @Test
    fun sourcesFranceAndEnglishAndEntertainment() {

    }
}