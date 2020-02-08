package org.isen.news.ctrl.jfx

import org.isen.news.ctrl.INewsControllerParams
import org.isen.news.model.impl.SourceModel

class SourceFxController : NewsDefaultFxController(), INewsControllerParams {
    init {
        super.models.add(SourceModel())
    }

    fun findSources(category: String?, language: String?, country: String?) {
        (super.models[0] as SourceModel).findSources(category, language, country)
    }

    override fun changeApiKey(key: String) {
        (super.models.first() as SourceModel).apiKey = key
    }

    override fun changePageSize(pageSize: Int) {
        (super.models.first() as SourceModel).pageSize = pageSize
    }

    override fun findErrorCode(): Int {
        return (super.models.first() as SourceModel).errorCode
    }
}