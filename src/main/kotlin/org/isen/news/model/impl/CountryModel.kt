package org.isen.news.model.impl

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.INewsModelObservable
import org.isen.news.model.data.Country
import kotlin.properties.Delegates

class CountryModel : DefaultNewsModel() {
    companion object : Logging

    private val allowedCountries: String = "ae;ar;at;au;be;bg;br;ca;ch;cn;co;cu;cz;de;eg;fr;gb;gr;hk;hu;id;ie;il;in;it;jp;kr;lt;lv;ma;mx;my;ng;nl;no;nz;ph;pl;pt;ro;rs;ru;sa;se;sg;si;sk;th;tr;tw;ua;us;ve;za"

    var countries: Array<Country> by Delegates.observable(arrayOf()) {
        property, oldValue, newValue ->
        logger.info("list of countries change, notify observer")
        for (listener in listeners) {
            listener.updateNews(newValue)
        }
    }

    override fun register(listener: INewsModelObservable) {
        listeners.add(listener)
    }

    override fun unregister(listener: INewsModelObservable) {
        listeners.remove(listener)
    }

    fun findCountries() {
        logger.info("get countries for request param")

        GlobalScope.launch { getCountriesFromAlpha2Codes(allowedCountries) }
    }

    private suspend fun getCountriesFromAlpha2Codes(codes: String) {
        logger.info("Get country $codes")
        logger.info("https://www.restcountries.eu/rest/v2/alpha?codes=${codes}")
        "https://www.restcountries.eu/rest/v2/alpha?codes=${codes}"
                .httpGet().responseObject(Country.Deserializer()) { request, response, result ->
                    logger.info("StatusCode:${response.statusCode}")
                    result.component1()?.let {
                        countries = it
                    }
                }
    }
}