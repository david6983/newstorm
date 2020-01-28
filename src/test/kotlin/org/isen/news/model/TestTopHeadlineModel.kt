package org.isen.news.model

import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.data.HeadlineRequest
import org.isen.news.model.impl.TopHeadlineModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestTopHeadlineModel {
    companion object : Logging

    @Test
    fun testBuildBreakingNewsQueryBuilder() {
        val model: TopHeadlineModel = TopHeadlineModel()
        // only country, page 1 (the country can't be wrong spelled because the user select it from a given selection)
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=fr", model.buildBreakingNewsQuery("fr", null, null, null))
        // only country, page 2
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=fr&page=2", model.buildBreakingNewsQuery("fr", null, 2, null))

        // no country, valid category, page 1, no keywords (same as country, the user select the category from a given list)
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&category=entertainment", model.buildBreakingNewsQuery(null, "entertainment", null, null))
        // no country, valid category, page 2, no keywords
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&category=science&page=2", model.buildBreakingNewsQuery(null, "science", 2, null))

        // no country, no category, page 1, no keywords
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}", model.buildBreakingNewsQuery(null, null, null, null))
        // no country, no category, page 2, no keywords
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&page=2", model.buildBreakingNewsQuery(null, null, 2, null))

        // no country, no category, page 1, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&q=Coronavirus", model.buildBreakingNewsQuery(null, null, null, "Coronavirus"))
        // no country, no category, page 1, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&q=american president", model.buildBreakingNewsQuery(null, null, null, "american president"))

        // no country, no category, page 2, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&q=Coronavirus&page=2", model.buildBreakingNewsQuery(null, null, 2, "Coronavirus"))
        // no country, no category, page 2, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&q=american president&page=2", model.buildBreakingNewsQuery(null, null, 2, "american president"))

        // 1 country, 1 category, page 1, no keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=us&category=science", model.buildBreakingNewsQuery("us", "science", null, null))
        // 1 country, 1 category, page 2, no keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=us&category=science&page=2", model.buildBreakingNewsQuery("us", "science", 2, null))

        // 1 country, no category, page 1, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=us&q=Coronavirus", model.buildBreakingNewsQuery("us", null, null, "Coronavirus"))
        // 1 country, no category, page 2, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=us&q=Coronavirus&page=2", model.buildBreakingNewsQuery("us", null, 2, "Coronavirus"))

        // 1 country, no category, page 1, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=us&q=american president", model.buildBreakingNewsQuery("us", null, null, "american president"))
        // 1 country, no category, page 2, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=us&q=american president&page=2", model.buildBreakingNewsQuery("us", null, 2, "american president"))

        // 1 country, 1 category, page 1, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=fr&category=entertainment&q=Coronavirus", model.buildBreakingNewsQuery("fr", "entertainment", null, "Coronavirus"))
        // 1 country, 1 category, page 2, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=fr&category=entertainment&q=Coronavirus&page=2", model.buildBreakingNewsQuery("fr", "entertainment", 2, "Coronavirus"))

        // 1 country, 1 category, page 1, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=fr&category=entertainment&q=american president", model.buildBreakingNewsQuery("fr", "entertainment", null, "american president"))
        // 1 country, 1 category, page 2, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&country=fr&category=entertainment&q=american president&page=2", model.buildBreakingNewsQuery("fr", "entertainment", 2, "american president"))

        // no country, 1 category, page 1, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&category=science&q=Coronavirus", model.buildBreakingNewsQuery(null, "science", null, "Coronavirus"))
        // no country, 1 category, page 2, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&category=science&q=Coronavirus&page=2", model.buildBreakingNewsQuery(null, "science", 2, "Coronavirus"))

        // no country, 1 category, page 1, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&category=science&q=american president", model.buildBreakingNewsQuery(null, "science", null, "american president"))
        // no country, 1 category, page 2, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&category=science&q=american president&page=2", model.buildBreakingNewsQuery(null, "science", 2, "american president"))
    }

    @Test
    fun testBuildBreakingNewsQueryBySourceBuilder() {
        val model: TopHeadlineModel = TopHeadlineModel()

        // only source, page 1 (the source can't be wrong spelled because the user select it from a given selection)
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news", model.buildBreakingNewsBySourceQuery("bbc-news", null, null))
        // only source, page 2
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news&page=2", model.buildBreakingNewsBySourceQuery("bbc-news", 2, null))

        // multi source, page 1 (the source can't be wrong spelled because the user select it from a given selection)
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news,abc-news", model.buildBreakingNewsBySourceQuery("bbc-news,abc-news", null, null))
        // multi source, page 2
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news,abc-news&page=2", model.buildBreakingNewsBySourceQuery("bbc-news,abc-news", 2, null))

        // only source, page 1, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news&q=virus", model.buildBreakingNewsBySourceQuery("bbc-news", null, "virus"))
        // only source, page 2, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news&q=virus&page=2", model.buildBreakingNewsBySourceQuery("bbc-news", 2, "virus"))
        // only source, page 1, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news&q=american president", model.buildBreakingNewsBySourceQuery("bbc-news", null, "american president"))
        // only source, page 2, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news&q=american president&page=2", model.buildBreakingNewsBySourceQuery("bbc-news", 2, "american president"))

        // multi source, page 1, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news,abc-news&q=virus", model.buildBreakingNewsBySourceQuery("bbc-news,abc-news", null, "virus"))
        // multi source, page 2, 1 keyword
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news,abc-news&q=virus&page=2", model.buildBreakingNewsBySourceQuery("bbc-news,abc-news", 2, "virus"))
        // multi source, page 1, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news,abc-news&q=american president", model.buildBreakingNewsBySourceQuery("bbc-news,abc-news", null, "american president"))
        // multi source, page 2, 1 sentence
        assertEquals("${TopHeadlineModel.TOP_HEADLINE_ROOT}?apiKey=${model.apiKey}&sources=bbc-news,abc-news&q=american president&page=2", model.buildBreakingNewsBySourceQuery("bbc-news,abc-news", 2, "american president"))
    }

    @Test
    fun testFindBreakingNews() {
        var passObserver: Boolean = false
        val model: TopHeadlineModel = TopHeadlineModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                // pour savoir si on a bien creer l'observeur
                passObserver = true;
                TestDefaultNewsModel.logger.info("updateNews with : $data")
                assertEquals(HeadlineRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findBreakingNews(null, "science", null, null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(passObserver, "after update top-headline on property, observer must receive location")
        assertEquals("ok", model.headlineRequest.status)
        assertNotNull(model.headlineRequest.articles)
        assertNotEquals(0, model.headlineRequest.totalResults)
    }

    @Test
    fun testFindBreakingNewsBySource() {
        var passObserver: Boolean = false
        val model: TopHeadlineModel = TopHeadlineModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                // pour savoir si on a bien creer l'observeur
                passObserver = true;
                TestDefaultNewsModel.logger.info("updateNews with : $data")
                assertEquals(HeadlineRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findBreakingNewsBySource("bbc-news", null, null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(passObserver, "after update top-headline on property, observer must receive location")
        assertEquals("ok", model.headlineRequest.status)
        assertNotNull(model.headlineRequest.articles)
        assertNotEquals(0, model.headlineRequest.totalResults)
    }
}