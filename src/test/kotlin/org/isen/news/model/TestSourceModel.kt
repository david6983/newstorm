package org.isen.news.model

import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.data.SourceRequest
import org.isen.news.model.impl.SourceModel
import org.isen.news.model.impl.TopHeadlineModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestSourceModel {
    companion object : Logging

    @Test
    fun testBuildSourceQuery() {
        val model: SourceModel = SourceModel()
        // category, page 1 (the country can't be wrong spelled because the user select it from a given selection)
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health", model.buildSourceQuery("health", null, null, null))
        // category, page 2
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&page=2", model.buildSourceQuery("health", null, null, 2))

        // language, page 1 (the language can't be wrong spelled because the user select it from a given selection)
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&language=fr", model.buildSourceQuery(null, "fr", null, null))
        // language, page 2
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&language=fr&page=2", model.buildSourceQuery(null, "fr", null, 2))

        // country, page 1 (the country can't be wrong spelled because the user select it from a given selection)
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&country=fr", model.buildSourceQuery(null, null, "fr", null))
        // country, page 2
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&country=fr&page=2", model.buildSourceQuery(null, null, "fr", 2))

        // category and language, page 1
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&language=fr", model.buildSourceQuery("health", "fr", null, null))
        // category and language, page 2
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&language=fr&page=2", model.buildSourceQuery("health", "fr", null, 2))

        // category and country, page 1
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&country=fr", model.buildSourceQuery("health", null, "fr", null))
        // category and country, page 2
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&country=fr&page=2", model.buildSourceQuery("health", null, "fr", 2))

        // language and country, page 1
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&language=fr&country=fr", model.buildSourceQuery(null, "fr", "fr", null))
        // language and country, page 2
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&language=fr&country=fr&page=2", model.buildSourceQuery(null, "fr", "fr", 2))

        // category and language and country, page 1
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&language=fr&country=fr", model.buildSourceQuery("health", "fr", "fr", null))
        // category and language and country, page 2
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&language=fr&country=fr&page=2", model.buildSourceQuery("health", "fr", "fr", 2))

    }
    
    @Test
    fun testObserverForSourceRequest() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)

        model.sourceRequest = SourceRequest()
        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
    }

    @Test
    fun findAllSources() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources(null, null, null, null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun findSourcesByCategory() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources("health", null, null, 2)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun findSourcesByLanguage() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources(null, "fr", null, null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun findSourcesByCountry() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources(null, null, "us", 3)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun findSourcesByCategoryAndLanguage() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources("science", "us", null, null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isEmpty())
        }
    }

    @Test
    fun findSourcesByCategoryAndLanguageUnsuccess() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources("science", "fr", null, null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isEmpty())
        }
    }

    @Test
    fun findSourcesByCategoryAndCountrySuccess() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources("science", null, "us", null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun findSourcesByCategoryAndCountryUnSuccess() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources("science", null, "fr", null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isEmpty())
        }
    }

    @Test
    fun findSourcesByLanguageAndCountrySuccess() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources(null, "en", "us", null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun findSourcesByLanguageAndCountryUnSuccess() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources(null, "fr", "us", null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isEmpty())
        }
    }

    @Test
    fun findSourcesByLanguageAndCountryAndCategorySuccess() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources("health", "en", "us", null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isNotEmpty())
        }
    }

    @Test
    fun findSourcesByLanguageAndCountryAndCategoryUnSuccess() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                TestCountryModel.logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findSources("health", "fr", "us", null)
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update sources on property, observer must receive sourceRequest"
        )
        assertEquals("ok", model.sourceRequest.status)
        assertNotNull(model.sourceRequest.sources)
        model.sourceRequest.sources?.let {
            assert(it.isEmpty())
        }
    }
}