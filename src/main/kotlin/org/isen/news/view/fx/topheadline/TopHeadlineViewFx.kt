package org.isen.news.view.fx.topheadline

import javafx.application.Platform
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import javafx.stage.StageStyle
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.ctrl.jfx.SourceFxController
import org.isen.news.ctrl.jfx.TopHeadlineFxController
import org.isen.news.model.data.*
import org.isen.news.view.INewsView
import org.isen.news.view.fx.fragments.ArticleViewFx
import org.isen.news.view.fx.menubar.NewsMainFxMenuBar
import tornadofx.*
import java.text.SimpleDateFormat

class TopHeadlineViewFx : View("Breaking News"), INewsView {
    companion object : Logging

    private val controller: TopHeadlineFxController by inject()
    private val sourceController: SourceFxController by inject()

    private val categories = Category.values().toList().asObservable()

    private var categorySelect: ComboBox<Category> by singleAssign()
    private var countrySelect: ComboBox<Country> by singleAssign()
    private var sourceSelect: ComboBox<Source> by singleAssign()
    private var keywordsArea: TextArea by singleAssign()
    private var keywordsAreaSource: TextArea by singleAssign()

    private var statusLabel: Label by singleAssign()

    private var filterDrawer: Drawer by singleAssign()

    private var pageLabel: Label by singleAssign()

    private var breakingNewsGrid: GridPane by singleAssign()

    private val formatter = SimpleDateFormat("dd/MMMMMM/yyyy hh:mm:ss")

    override val root = borderpane {
        top<NewsMainFxMenuBar>()
        left {
            drawer {
                filterDrawer = this
                multiselect = false
                item("Filters", expanded = true) {
                    vbox {
                        form {
                            fieldset("Filters") {
                                vbox {
                                    field("Category") {
                                        combobox<Category> {
                                            categorySelect = this
                                            items = categories
                                            value = Category.GENERAL
                                            this.valueProperty().onChange {
                                                controller.findBreakingNews(
                                                        countrySelect.selectedItem?.alpha2Code,
                                                        categorySelect.selectedItem?.title,
                                                        null,
                                                        keywordsArea.text
                                                )
                                                pageLabel.text = "1"
                                            }
                                        }
                                    }
                                    field("Country") {
                                        combobox<Country> {
                                            countrySelect = this
                                            prefWidth = 120.0
                                            this.valueProperty().onChange {
                                                controller.findBreakingNews(
                                                        countrySelect.selectedItem?.alpha2Code,
                                                        categorySelect.selectedItem?.title,
                                                        null,
                                                        keywordsArea.text
                                                )
                                                pageLabel.text = "1"
                                            }
                                        }
                                    }
                                    field("Keywords") {
                                        textarea {
                                            keywordsArea = this
                                            prefWidth = 120.0
                                            this.textProperty().onChange {
                                                controller.findBreakingNews(
                                                        countrySelect.selectedItem?.alpha2Code,
                                                        categorySelect.selectedItem?.title,
                                                        null,
                                                        keywordsArea.text
                                                )
                                                pageLabel.text = "1"
                                            }
                                        }
                                    }
                                    button("Filter").action {
                                        controller.findBreakingNews(
                                                countrySelect.selectedItem?.alpha2Code,
                                                categorySelect.selectedItem?.title,
                                                null,
                                                keywordsArea.text
                                        )
                                        pageLabel.text = "1"
                                    }
                                }
                            }
                        }
                    }
                }
                item("Filter by source", expanded = false) {
                    vbox {
                        form {
                            fieldset("Filters") {
                                vbox {
                                    field("Source") {
                                        combobox<Source> {
                                            sourceSelect = this
                                            prefWidth = 120.0
                                            this.valueProperty().onChange {
                                                controller.findBreakingNewsBySource(
                                                        sourceSelect.value.toString(),
                                                        null,
                                                        keywordsAreaSource.text
                                                )
                                                pageLabel.text = "1"
                                            }
                                        }
                                    }
                                    field("Keywords") {
                                        textarea {
                                            keywordsAreaSource = this
                                            prefWidth = 120.0
                                            this.textProperty().onChange {
                                                controller.findBreakingNewsBySource(
                                                        sourceSelect.value.toString(),
                                                        null,
                                                        keywordsAreaSource.text
                                                )
                                                pageLabel.text = "1"
                                            }
                                        }
                                    }
                                    button("Filter").action {
                                        controller.findBreakingNewsBySource(
                                                sourceSelect.value.toString(),
                                                null,
                                                keywordsAreaSource.text
                                        )
                                        pageLabel.text = "1"
                                    }
                                }
                            }
                        }
                    }
                }
                //prefWidth = 240.0
            }
        }
        center {
            borderpane {
                top {
                    label("Breaking news") {
                        style {
                            fontSize = 42.px
                        }
                    }
                }
                center {
                    scrollpane {
                        gridpane {
                            breakingNewsGrid = this
                        }
                        //prefWidth = 800.0
                    }
                }
                bottom {
                    flowpane {
                        button("Previous page").action {
                            if (pageLabel.text.toInt() > 1) {
                                pageLabel.text = (pageLabel.text.toInt() - 1).toString()
                                if (!filterDrawer.multiselect) {
                                    if (filterDrawer.items.first().expanded) {
                                        controller.findBreakingNews(
                                                countrySelect.selectedItem?.alpha2Code,
                                                categorySelect.selectedItem?.title,
                                                pageLabel.text.toInt(),
                                                ""
                                        )
                                    } else if (filterDrawer.items.last().expanded) {
                                        controller.findBreakingNewsBySource(
                                                sourceSelect.value.toString(),
                                                pageLabel.text.toInt(),
                                                keywordsAreaSource.text
                                        )
                                    }
                                } else {
                                    alert(Alert.AlertType.WARNING,
                                "Multiselection not allowed",
                                "You can't find breaking news by source " +
                                        "and country, category ! Disable " +
                                        "multiselect option"
                                    )
                                }
                            }
                        }
                        label("1") {
                            pageLabel = this
                            style {
                                fontSize = 14.px
                                fontWeight = FontWeight.BOLD
                            }
                        }
                        button("Next page").action {
                            pageLabel.text = (pageLabel.text.toInt() + 1).toString()
                            if (!filterDrawer.multiselect) {
                                if (filterDrawer.items.first().expanded) {
                                    controller.findBreakingNews(
                                            countrySelect.selectedItem?.alpha2Code,
                                            categorySelect.selectedItem?.title,
                                            pageLabel.text.toInt(),
                                            ""
                                    )
                                } else if (filterDrawer.items.last().expanded) {
                                    controller.findBreakingNewsBySource(
                                            sourceSelect.value.toString(),
                                            pageLabel.text.toInt(),
                                            keywordsAreaSource.text
                                    )
                                }
                            } else {
                                alert(Alert.AlertType.WARNING,
                                        "Multiselection not allowed",
                                        "You can't find breaking news by source and country, category ! Disable multiselect option"
                                )
                            }
                        }
                        hgap = 550.0
                        paddingAll = 15
                    }
                }
            }
        }
        bottom {
            label {
                statusLabel = this
                paddingAll = 10
            }
        }
    }

    private fun updateSources(data: SourceRequest) {
        Platform.runLater {
            logger.info("updating sources")
            data.sources?.let { sourceSelect.items.addAll(it) }
        }
    }

    private fun updateBreakingNews(data: HeadlineRequest) {
        Platform.runLater {
            logger.info("clearing news view")
            breakingNewsGrid.children.clear()
            logger.info("updating breaking news")
            if (!data.articles.isNullOrEmpty()) {
                data.articles.withIndex().forEach { (index, item) ->
                    logger.info("updateNews $item")
                    breakingNewsGrid.addRow(index, borderpane {
                        top {
                            label(item.title) {
                                style {
                                    fontSize = 18.px
                                    fontWeight = FontWeight.BOLD
                                }
                                paddingAll = 10
                                textAlignment = TextAlignment.CENTER
                            }
                        }
                        bottom {
                            gridpane {
                                row {
                                    if (item.author != null) {
                                        label(item.author)
                                    } else {
                                        label("Unknown author")
                                    }
                                    label(formatter.format(item.publishedAt))
                                    button("Open") {
                                        addClass("btn-info", "btn-lg")
                                        action {
                                            find<ArticleViewFx>(mapOf("article" to item)).openWindow(
                                                    stageStyle = StageStyle.UTILITY
                                            )
                                        }
                                    }
                                    hgap = 450.0
                                }
                            }
                            paddingAll = 15
                        }
                        center {
                            text(item.description) {
                                style {
                                    fontStyle = FontPosture.ITALIC
                                    fontSize = 12.px
                                }
                            }
                        }
                        style {
                            borderColor += box(c(0, 0, 0))
                        }
                    })
                }
            } else {
                logger.info("no results found")
                breakingNewsGrid.addRow(0, pane {
                    label("No results found")
                })
            }
        }
    }

    private fun updateStatusCode(code: Int) {
        Platform.runLater {
            statusLabel.text = "Status code : " + when(code) {
                200 -> "$code - message : ok"
                401 -> "$code - message : api key error unauthorized"
                429 -> "$code - message : too many requests for today"
                else -> "$code - message : error"
            }
        }
    }

    init {
        controller.registerView(this)
        sourceController.registerView(this)

        with(root) {
            prefWidth = 1580.0
            prefHeight = 800.0
        }

        controller.findCountries()
        logger.info("all countries names fetched successfully")
        controller.findBreakingNews("fr", "general", null, null)
        logger.info("breaking news found for France (fr)")
        sourceController.findSources(null, null, null)
        logger.info("all sources names fetched successfully")
    }

    override fun display() {
        this.display()
    }

    override fun updateNews(data: Any) {
        when (data) {
            is HeadlineRequest -> {
                logger.info("receive breaking news")
                updateBreakingNews(data)
            }
            is Array<*> -> {
                data.forEach {
                    logger.info("received country ${it as Country} in top headline")
                    countrySelect.items.add(it)
                }
                Platform.runLater {
                    countrySelect.value = controller.indexFromCode("fr")?.let {
                        countrySelect.items[it]
                    }
                }
            }
            is SourceRequest -> {
                logger.info("receive sources $data")
                updateSources(data)
            }
            is Int -> {
                logger.info("receive error code $data")
                updateStatusCode(data)
            }
            else -> {
                logger.info("data : $data")
            }
        }
    }
}