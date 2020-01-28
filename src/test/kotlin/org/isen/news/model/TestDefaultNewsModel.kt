package org.isen.news.model

import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.data.HeadlineRequest
import org.isen.news.model.impl.DefaultNewsModel
import org.isen.news.model.impl.TopHeadlineModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestDefaultNewsModel {
    companion object : Logging

    @Test
    fun testObserverForPageSize() {
        var passObserver: Boolean = false
        val model: DefaultNewsModel = DefaultNewsModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                // pour savoir si on a bien creer l'observeur
                passObserver = true;
                logger.info("updateNews with : $data")
                assert(data is Int)
            }
        }

        model.register(myObserver)

        model.pageSize = 5
        assertTrue(passObserver, "after update pageSize on property, observer must receive pageSize")
    }

    @Test
    fun testObserverForApiKey() {
        var passObserver: Boolean = false
        val model: DefaultNewsModel = DefaultNewsModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                // pour savoir si on a bien creer l'observeur
                passObserver = true;
                logger.info("updateNews with : $data")
                assert(data is String)
            }
        }

        model.register(myObserver)

        model.apiKey = "f71bcc87b1474fd381f02f588c1ce353"
        assertTrue(passObserver, "after update apiKey on property, observer must receive apiKey")
    }
}