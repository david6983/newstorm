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

class TestTopHeadlineRequest {
    @Before
    fun init() {
        FuelManager.instance.basePath = "https://newsapi.org/v2/"
    }

    @Test
    fun breakingNewsFrance() {
        val (request, response, result) = "top-headlines?country=fr&apiKey=$NEWS_API_KEY"
                .httpGet().responseObject(HeadlineRequest.Deserializer())
        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.articles.also {
                assertNotNull(it)
                assert(it.isNotEmpty())
            }
        }
    }

    @Test
    fun breakingNewsEntertainment() {
        val (request, response, result) = "top-headlines?category=entertainment&apiKey=$NEWS_API_KEY"
                .httpGet().responseObject(HeadlineRequest.Deserializer())
        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.articles.also {
                assertNotNull(it)
                assert(it.isNotEmpty())
            }
        }
    }

    @Test
    fun byKeywords() {
        val (request, response, result) = "top-headlines?q=corona&apiKey=$NEWS_API_KEY"
                .httpGet().responseObject(HeadlineRequest.Deserializer())
        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.articles.also {
                assertNotNull(it)
                assert(it.isNotEmpty())
            }
        }
    }

    @Test
    fun noResultFromKeyword() {
        val (request, response, result) = "top-headlines?q=ejzfioefjzeiofjzeio&apiKey=$NEWS_API_KEY"
                .httpGet().responseObject(HeadlineRequest.Deserializer())
        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.articles.also {
                assertNotNull(it)
                assert(it.isEmpty())
            }
        }
    }

    @Test
    fun pageSizeAndPage() {
        val (request, response, result) = "top-headlines?country=us&page=2&pageSize=5&apiKey=$NEWS_API_KEY"
                .httpGet().responseObject(HeadlineRequest.Deserializer())
        assertTrue(response.isSuccessful)

        result.component1().also {
            assertNotNull(it).also { println(it) }
        } ?.let {
            assertEquals("ok", it.status)
            it.articles.also {
                assertNotNull(it)
                assert(it.isNotEmpty())
            }
        }
    }
}