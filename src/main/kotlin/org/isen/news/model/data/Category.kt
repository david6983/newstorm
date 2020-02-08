package org.isen.news.model.data

/**
 * categories available to search an article
 *
 * @property title
 */
enum class Category(val title: String) {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology");

    /**
     * By default the title is displayed
     *
     * @return title
     */
    override fun toString(): String {
        return title
    }
}