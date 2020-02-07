package org.isen.news.ctrl.jfx

import org.isen.news.ctrl.jfx.NewsDefaultFxController
import org.isen.news.model.impl.SourceModel

class SourceFxController : NewsDefaultFxController() {
    init {
        super.models.add(SourceModel())
    }

    fun findSources(category: String?, language: String?, country: String?, page: Int?) {
        (super.models[0] as SourceModel).findSources(category, language, country, page)
    }
}