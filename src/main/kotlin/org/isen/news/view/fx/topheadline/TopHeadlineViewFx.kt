package org.isen.news.view.fx.topheadline

import javafx.application.Platform
import javafx.geometry.Pos
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

    private val headlineFxController: TopHeadlineFxController by inject()
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
                                                headlineFxController.findBreakingNews(
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
                                                headlineFxController.findBreakingNews(
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
                                                headlineFxController.findBreakingNews(
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
                                        headlineFxController.findBreakingNews(
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
                                                headlineFxController.findBreakingNewsBySource(
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
                                                headlineFxController.findBreakingNewsBySource(
                                                        sourceSelect.value.toString(),
                                                        null,
                                                        keywordsAreaSource.text
                                                )
                                                pageLabel.text = "1"
                                            }
                                        }
                                    }
                                    button("Filter").action {
                                        headlineFxController.findBreakingNewsBySource(
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
                    hbox {
                        label("Breaking news") {
                            style {
                                fontSize = 42.px
                            }
                        }
                        alignment = Pos.CENTER
                        paddingAll = 10
                    }
                }
                center {
                    scrollpane(fitToWidth = true) {
                        gridpane {
                            breakingNewsGrid = this
                        }
                    }
                }
                bottom {
                    hbox {
                        button("Previous page") {
                            prefWidth = 1920 / 3.0
                        }.action {
                            if (pageLabel.text.toInt() > 1) {
                                pageLabel.text = (pageLabel.text.toInt() - 1).toString()
                                if (!filterDrawer.multiselect) {
                                    if (filterDrawer.items.first().expanded) {
                                        headlineFxController.findBreakingNews(
                                                countrySelect.selectedItem?.alpha2Code,
                                                categorySelect.selectedItem?.title,
                                                pageLabel.text.toInt(),
                                                ""
                                        )
                                    } else if (filterDrawer.items.last().expanded) {
                                        headlineFxController.findBreakingNewsBySource(
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
                            alignment = Pos.CENTER
                            prefWidth = 1920 / 3.0
                        }
                        button("Next page") {
                            prefWidth = 1920 / 3.0
                        }.action {
                            pageLabel.text = (pageLabel.text.toInt() + 1).toString()
                            if (!filterDrawer.multiselect) {
                                if (filterDrawer.items.first().expanded) {
                                    headlineFxController.findBreakingNews(
                                            countrySelect.selectedItem?.alpha2Code,
                                            categorySelect.selectedItem?.title,
                                            pageLabel.text.toInt(),
                                            ""
                                    )
                                } else if (filterDrawer.items.last().expanded) {
                                    headlineFxController.findBreakingNewsBySource(
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
                        //hgap = 550.0
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
            if (!data.articles.isNullOrEmpty() && data.status == "ok") {
                data.articles.withIndex().forEach { (index, item) ->
                    logger.info("updateNews $item")
                    breakingNewsGrid.addRow(index,
                            borderpane {
                                top {
                                    hbox {
                                        label(item.title) {
                                            style {
                                                fontSize = 18.px
                                                fontWeight = FontWeight.BOLD
                                            }
                                            paddingAll = 15
                                            isWrapText = true
                                        }
                                        alignment = Pos.CENTER
                                    }
                                }
                                bottom {
                                    hbox {
                                        if (item.author != null) {
                                            label(item.author) {
                                                prefWidth = 1920 / 3.0
                                                alignment = Pos.CENTER
                                            }
                                        } else {
                                            label("Unknown author") {
                                                prefWidth = 1920 / 3.0
                                                alignment = Pos.CENTER
                                            }
                                        }
                                        label(formatter.format(item.publishedAt)) {
                                            prefWidth = 1920 / 3.0
                                            alignment = Pos.CENTER
                                        }
                                        button("Open") {
                                            prefWidth = 1920 / 3.0
                                            alignment = Pos.CENTER
                                            action {
                                                find<ArticleViewFx>(mapOf("article" to item)).openWindow(
                                                        stageStyle = StageStyle.DECORATED
                                                )
                                            }
                                        }
                                    }
                                    paddingAll = 15
                                }
                                center {
                                    hbox {
                                        item.description?.let {
                                            label(it) {
                                                isWrapText = true
                                                style {
                                                    fontStyle = FontPosture.ITALIC
                                                    fontSize = 16.px
                                                }
                                            }
                                        }
                                        paddingAll = 15
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

    override fun updateStatusCode(code: Int) {
        logger.info("receive error code $code")
        Platform.runLater {
            statusLabel.text = "Status code : " + when (code) {
                200 -> "$code - OK. The request was executed successfully"
                401 -> "$code - Unauthorized. Your API key was missing from " +
                        "the request, or wasn't correct"
                400 -> "$code - Bad Request. The request was unacceptable, " +
                        "often due to a missing or misconfigured parameter"
                429 -> "$code - Too Many Requests. You made too many requests " +
                        "within a window of time and have been rate limited. " +
                        "Back off for a while"
                500 -> "$code - Server Error. Something went wrong on our side"
                else -> "$code - message : error"
            }
        }
    }

    init {
        headlineFxController.registerView(this)
        sourceController.registerView(this)

        with(root) {
            prefWidth = 1580.0
            prefHeight = 800.0
        }

        headlineFxController.findCountries()
        logger.info("all countries names fetched successfully")
        headlineFxController.findBreakingNews("fr", "general", null, null)
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
                    countrySelect.value = headlineFxController.indexFromCode("fr")?.let {
                        countrySelect.items[it]
                    }
                }
            }
            is SourceRequest -> {
                logger.info("receive sources $data")
                updateSources(data)
            }
            else -> {
                logger.info("data : $data")
            }
        }
    }


}