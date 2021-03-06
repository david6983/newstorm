package org.isen.news.view

import javafx.geometry.Insets
import org.isen.news.model.data.Article
import tornadofx.*
import java.util.*

class TestArticleFragment : Fragment("Article") {
    private val article: Article by param()

    override val root = vbox {
        label(article.title) {
            style {
                padding = box(20.px)
            }
        }
    }
}

class TestFxFragmentParamView : View() {
    private var item: Article = Article(null,
            null,
            "hello article",
            "",
            "",
            "",
            Date(),
            null)

    override val root = hbox {
        button("open article") {
            action {
                find<TestArticleFragment>(mapOf("article" to item)).openWindow()
            }
        }
        button("Lorem") {

        }
    }
}

class TestFxFragmentParamApp : App(TestFxFragmentParamView::class) {

}

fun main(args: Array<String>) {
    launch<TestFxFragmentParamApp>(args)
}
