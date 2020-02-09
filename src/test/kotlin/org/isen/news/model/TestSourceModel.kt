package org.isen.news.model

import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.data.SourceRequest
import org.isen.news.model.impl.SourceModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestSourceModel {
    companion object : Logging

    @Test
    fun testBuildSourceQuery() {
        val model = SourceModel()
        // category, page 1 (the country can't be wrong spelled because the user select it from a given selection)
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health", model.buildSourceQuery("health", null, null))

        // language, page 1 (the language can't be wrong spelled because the user select it from a given selection)
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&language=fr", model.buildSourceQuery(null, "fr", null))

        // country, page 1 (the country can't be wrong spelled because the user select it from a given selection)
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&country=fr", model.buildSourceQuery(null, null, "fr"))

        // category and language, page 1
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&language=fr", model.buildSourceQuery("health", "fr", null))

        // category and country, page 1
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&country=fr", model.buildSourceQuery("health", null, "fr"))

        // language and country, page 1
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&language=fr&country=fr", model.buildSourceQuery(null, "fr", "fr"))

        // category and language and country, page 1
        assertEquals("${SourceModel.SOURCE_ROOT}?apiKey=${model.apiKey}&category=health&language=fr&country=fr", model.buildSourceQuery("health", "fr", "fr"))
    }
    
    @Test
    fun testObserverForSourceRequest() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)

        model.sourceRequest = SourceRequest()
        assertTrue(
                passObserver,
                "after update sources on property, observer must receive " +
                        "sourceRequest")

    }

    @Test
    fun findAllSources() {
        var passObserver: Boolean = true
        val model: SourceModel = SourceModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources(null, null, null)
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources("health", null, null)
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources(null, "fr", null)
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources(null, null, "us")
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources("science", "us", null)
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources("science", "fr", null)
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources("science", null, "us")
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources("science", null, "fr")
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources(null, "en", "us")
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources(null, "fr", "us")
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources("health", "en", "us")
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
                logger.info("updateNews with : $data")
                assertEquals(SourceRequest::class.java, data::class.java)
            }

            override fun updateStatusCode(code: Int) {
                TestTopHeadlineModel.logger.info("updateStatusCode with : $code")
            }
        }

        model.register(myObserver)
        model.findSources("health", "fr", "us")
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