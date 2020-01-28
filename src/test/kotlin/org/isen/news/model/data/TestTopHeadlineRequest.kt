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
            assertEquals(34, it.totalResults)
            it.articles.also {
                assertNotNull(it)
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
            assertEquals(3185, it.totalResults)
            it.articles.also {
                assertNotNull(it)
            }
        }
    }

    @Test
    fun countryAndSource() {

    }

    @Test
    fun categoryAndSource() {

    }

    @Test
    fun byKeywords() {

    }

    @Test
    fun pageSizeAndPage() {

    }
}