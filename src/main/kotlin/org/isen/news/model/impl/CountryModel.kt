package org.isen.news.model.impl

import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.INewsModelObservable
import org.isen.news.model.data.Country
import kotlin.properties.Delegates

/**
 * Manage the countries
 */
class CountryModel : DefaultNewsModel() {
    companion object : Logging

    /**
     * list of allowed countries as a string
     */
    private val allowedCountries: String = "ae;ar;at;au;be;bg;br;ca;ch;cn;co;cu;cz;de;eg;fr;gb;gr;hk;hu;id;ie;il;in;it;jp;kr;lt;lv;ma;mx;my;ng;nl;no;nz;ph;pl;pt;ro;rs;ru;sa;se;sg;si;sk;th;tr;tw;ua;us;ve;za"

    /**
     * countries property
     */
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

    /**
     * find the full names of the allowed countries by using restcountries API
     */
    fun findCountries() {
        logger.info("get countries for request param")

        // launch the coroutine in a dedicated thread
        GlobalScope.launch { getCountriesFromAlpha2Codes(allowedCountries) }
    }

    /**
     * get the list of countries from the api by giving the alpha2codes
     *
     * @param codes
     */
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

    /**
     * return the index if exist of a country in the list of allowed countries
     *
     * @param alpha2code code of the country to search
     * @return index of the country in the list of allowed countries
     */
    fun indexOfAllowedCountryFromAlpha2code(alpha2code: String): Int? {
        val countriesCodes: List<String> = allowedCountries.split(";")
        allowedCountries.split(";").find {
            it == alpha2code
        } ?.let {
            return countriesCodes.indexOf(it)
        }
        return null
    }
}