package org.isen.news.view.fx.fragments

import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.data.Article
import tornadofx.*
import java.text.SimpleDateFormat

/**
 * View fragment of an article
 */
class ArticleViewFx : Fragment("Article") {
    companion object : Logging

    /**
     * the article information are given by parameters
     */
    private val article: Article by param()

    private val formatter = SimpleDateFormat("dd/MMMMMM/yyyy hh:mm:ss")

    init {
        title = article.title
        title += " ${formatter.format(article.publishedAt)}"
    }

    override val root = borderpane {
        top {
            hbox {
                // article title
                label(article.title) {
                    style {
                        fontWeight = FontWeight.BOLD
                        fontSize = 16.px
                        textFill = c(255, 255, 255)
                    }
                    isWrapText = true
                    paddingAll = 15
                }
                style {
                    backgroundColor += c(0, 0, 0)
                }
                alignment = Pos.CENTER
            }
        }
        center {
            vbox {
                // article image
                hbox {
                    imageview(article.urlToImage) {
                        isPreserveRatio = true
                        fitWidth = 800.0
                        alignment = Pos.CENTER
                    }

                }
                hbox {
                    // article content
                    article.content?.let {
                        textflow {
                            paddingAll = 30
                            text(it.dropLast(it.length - 1)) {
                                font = Font("Serif", 34.0)
                                style {
                                    fontWeight = FontWeight.BOLD
                                }
                            }
                            text(it.drop(1)) {
                                font = Font("Serif", 18.0)
                            }
                        }
                    }
                    if (article.content.isNullOrEmpty()) {
                        label("no content (read it on the official website)")
                    }
                    alignment = Pos.CENTER
                }
            }
        }
        bottom {
            hbox {
                // article author
                if (article.author != null) {
                    label(article.author!!) {
                        paddingAll = 10
                        prefWidth = 1920/3.0
                        alignment = Pos.CENTER
                    }
                } else {
                    label("Unknown author") {
                        paddingAll = 10
                        prefWidth = 1920/3.0
                        alignment = Pos.CENTER
                    }
                }
                // article date
                label(formatter.format(article.publishedAt)) {
                    paddingAll = 10
                    prefWidth = 1920/3.0
                    alignment = Pos.CENTER
                }
                // read online button that redirect to the website
                button("Read online"){
                    paddingAll = 10
                    prefWidth = 1920/3.0
                    alignment = Pos.CENTER
                }.action {
                    hostServices.showDocument(article.url)
                }
                paddingAll = 15
                fitToParentWidth()
            }
        }
    }

    init {
        with (root) {
            prefWidth = 800.0
            prefHeight = 900.0
        }
    }
}