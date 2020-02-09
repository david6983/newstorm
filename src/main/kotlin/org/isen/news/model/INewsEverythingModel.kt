package org.isen.news.model

import org.isen.news.model.data.EverythingQuery

interface INewsEverythingModel {
    fun findArticles(query: EverythingQuery)
}