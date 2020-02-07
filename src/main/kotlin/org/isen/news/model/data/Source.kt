package org.isen.news.model.data

data class Source(
        val id: String?,
        val name: String,
        val description: String,
        val url: String,
        val category: String,
        val language: String,
        val country: String
) {
    override fun toString(): String {
        return id ?: ""
    }
}