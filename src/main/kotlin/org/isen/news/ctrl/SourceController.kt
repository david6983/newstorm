package org.isen.news.ctrl

import org.isen.news.model.impl.SourceModel

class SourceController(
sourceModel: SourceModel
) : NewsDefaultController() {
    init {
        super.models.add(sourceModel)
    }

    fun findSources(category: String?, language: String?, country: String?) {
        (super.models[0] as SourceModel).findSources(category, language, country)
    }
}