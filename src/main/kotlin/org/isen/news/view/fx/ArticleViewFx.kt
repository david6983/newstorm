package org.isen.news.view.fx

import javafx.scene.text.FontWeight
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.model.data.Article
import tornadofx.*
import java.text.SimpleDateFormat

class ArticleViewFx : Fragment("Article") {
    companion object : Logging

    private val article: Article by param()

    private val formatter = SimpleDateFormat("dd/MMMMMM/yyyy hh:mm:ss")

    init {
        title = article.title
        title += " ${formatter.format(article.publishedAt)}"
    }

    override val root = borderpane {
        top {
            label(article.title) {
                style {
                    fontWeight = FontWeight.BOLD
                    fontSize = 16.px
                }
                paddingAll = 15
            }
        }
        center {
            gridpane {
                row {
                    imageview(article.urlToImage) {
                        isPreserveRatio = true
                        fitWidth = 800.0
                    }
                }
                row {
                    label(article.description) {
                        style {
                            fontSize = 16.px
                        }
                    }
                }
                row {
                    article.content?.let { label(it)  }
                    if (article.content.isNullOrEmpty()) {
                        label("no content (read it on the official website")
                    }
                }
            }
        }
        bottom {
            gridpane {
                row {
                    if (article.author != null) {
                        label(article.author!!) {
                            paddingAll = 10
                        }
                    } else {
                        label("Unknown author") {
                            paddingAll = 10
                        }
                    }
                    label(formatter.format(article.publishedAt))
                    button("Read online").action {
                        hostServices.showDocument(article.url)
                    }
                }
                hgap = 220.0
            }
        }
    }

    init {
        with (root) {
            prefWidth = 800.0
            prefHeight = 600.0
        }
    }
}