package org.isen.news.model

interface INewsSourceModel {
    fun findSources(category: String?, language: String?, country: String?)
}