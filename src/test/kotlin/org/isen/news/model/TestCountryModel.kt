package org.isen.news.model

import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.data.Country
import org.isen.news.model.impl.CountryModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TestCountryModel {
    companion object : Logging

    @Test
    fun testObserver() {
        var passObserver: Boolean = true
        val model: CountryModel = CountryModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                logger.info("updateNews with : $data")
                assertEquals(Array<Country>::class.java, data::class.java)
            }
        }

        model.register(myObserver)

        model.countries = arrayOf()
        assertTrue(
                passObserver,
                "after update country on property, observer must receive countries"
        )
    }

    @Test
    fun testFindCountries() {
        var passObserver: Boolean = true
        val model: CountryModel = CountryModel()

        val myObserver = object : INewsModelObservable {
            override fun updateNews(data: Any) {
                passObserver = true;
                logger.info("updateNews with : $data")
                assertEquals(Country::class.java, data::class.java)
            }
        }

        model.register(myObserver)
        model.findCountries()
        logger.info("wait data ...")
        Thread.sleep(10000)

        assertTrue(
                passObserver,
                "after update country on property, observer must receive " +
                        "country"
        )
        assertEquals(54, model.countries.size)
        model.countries.forEach {
            assertNotNull(it)
        }
        assertEquals("United Arab Emirates", model.countries.first().name)
        assertEquals("AE", model.countries.first().alpha2Code)
        assertEquals("https://restcountries.eu/data/are.svg", model.countries.first().flag)
        assertEquals("South Africa", model.countries.last().name)
        assertEquals("ZA", model.countries.last().alpha2Code)
        assertEquals("https://restcountries.eu/data/zaf.svg", model.countries.last().flag)
    }

    @Test
    fun testIndexFromAlpha2code() {
        val model: CountryModel = CountryModel()
        assertEquals(3, model.indexOfAllowedCountryFromAlpha2code("au"))
        assertNull(model.indexOfAllowedCountryFromAlpha2code("fejif"))
    }
}