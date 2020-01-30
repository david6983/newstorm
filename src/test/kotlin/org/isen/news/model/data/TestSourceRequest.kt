package org.isen.news.model.data

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import org.isen.news.util.NEWS_API_KEY
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
                assertTrue(it.isNotEmpty())
            }
        }
    }

    @Test
    fun allSourcesFrance() {
        val (request, response, result) = "sources?apiKey=$NEWS_API_KEY&country=fr"
                .httpGet().responseObject(SourceRequest.Deserializer())

        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.sources.also {
                assertNotNull(it)
            } ?.let {
                assertTrue(it.isNotEmpty())
            }
        }
    }

    @Test
    fun allSourcesFranceAndEntertainment() {
        val (request, response, result) = "sources?apiKey=$NEWS_API_KEY&country=fr&category=entertainment"
                .httpGet().responseObject(SourceRequest.Deserializer())

        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.sources.also {
                assertNotNull(it)
            } ?.let {
                assertFalse(it.isNotEmpty())
            }
        }
    }

    @Test
    fun sourcesFranceAndEnglish() {
        val (request, response, result) = "sources?apiKey=$NEWS_API_KEY&country=fr&language=en"
                .httpGet().responseObject(SourceRequest.Deserializer())

        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.sources.also {
                assertNotNull(it)
            } ?.let {
                assertFalse(it.isNotEmpty())
            }
        }
    }

    @Test
    fun sourcesFranceAndEnglishAndEntertainment() {
        val (request, response, result) = "sources?apiKey=$NEWS_API_KEY&country=fr&language=fr&category=entertainment"
                .httpGet().responseObject(SourceRequest.Deserializer())

        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.sources.also {
                assertNotNull(it)
            } ?.let {
                assertFalse(it.isNotEmpty())
            }
        }
    }

    @Test
    fun sourcesCountryAndLanguageNotCompatible() {
        val (request, response, result) = "sources?apiKey=$NEWS_API_KEY&country=fr&language=ro"
                .httpGet().responseObject(SourceRequest.Deserializer())

        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.sources.also {
                assertNotNull(it)
            } ?.let {
                assertTrue(it.isEmpty())
            }
        }
    }
}