package org.isen.news.view.fx

import javafx.application.Platform
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.stage.StageStyle
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.ctrl.jfx.SourceFxController
import org.isen.news.ctrl.jfx.TopHeadlineFxController
import org.isen.news.model.data.*
import org.isen.news.view.INewsView
import tornadofx.*
import java.text.SimpleDateFormat

class SearchSourceViewFx : View("Search a source"), INewsView {
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

    private var sourceGrid: GridPane by singleAssign()

    private val formatter = SimpleDateFormat("dd/MMMMMM/yyyy hh:mm:ss")

    override val root = borderpane {
        left {
            drawer {
                filterDrawer = this
                multiselect = false
                item("Filters", expanded = true) {
                    vbox {
                        form {
                            fieldset("Filters") {
                                vbox {
                                    field("Keywords") {
                                        textarea() {
                                            keywordsArea = this
                                            prefWidth = 120.0
                                            this.textProperty().onChange {
                                                //headlineFxController.findBreakingNews(countrySelect.selectedItem?.alpha2Code, categorySelect.selectedItem?.title, null, keywordsArea.text)
                                            }
                                        }
                                    }
                                    field("title") {
                                        textfield()
                                    }
                                    field("Category") {
                                        combobox<Category> {
                                            categorySelect = this
                                            items = categories
                                            value = Category.GENERAL
                                            this.valueProperty().onChange {
                                                //headlineFxController.findBreakingNews(countrySelect.selectedItem?.alpha2Code, categorySelect.selectedItem?.title, null, keywordsArea.text)
                                            }
                                        }
                                    }
                                    field("Country") {
                                        combobox<Country> {
                                            countrySelect = this
                                            prefWidth = 120.0
                                            this.valueProperty().onChange {
                                                //headlineFxController.findBreakingNews(countrySelect.selectedItem?.alpha2Code, categorySelect.selectedItem?.title, null, keywordsArea.text)
                                            }
                                        }
                                    }
                                    button("Filter").action {
                                        //headlineFxController.findBreakingNews(countrySelect.selectedItem?.alpha2Code, categorySelect.selectedItem?.title, null, keywordsArea.text)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        center {
            borderpane {
                top {
                    label("Results") {
                        style {
                            fontSize = 42.px
                        }
                    }
                }
                center {
                    scrollpane {
                        gridpane {
                            sourceGrid = this
                        }
                        prefWidth = 800.0
                    }
                }
                bottom {
                    hbox {
                        button("Previous page").action {
                            if (pageLabel.text.toInt() > 1) {
                                pageLabel.text = (pageLabel.text.toInt() - 1).toString()
                                //headlineFxController.findBreakingNews(countrySelect.selectedItem?.alpha2Code, categorySelect.selectedItem?.title, pageLabel.text.toInt(), "")
                            }
                        }
                        label("1") {
                            pageLabel = this
                        }
                        button("Next page").action {
                            pageLabel.text = (pageLabel.text.toInt() + 1).toString()
                            //headlineFxController.findBreakingNews(countrySelect.selectedItem?.alpha2Code, categorySelect.selectedItem?.title, pageLabel.text.toInt(), "")
                        }

                        prefWidth = 800.0
                    }
                }
            }
        }
        bottom {
            label() {
                statusLabel = this
            }
        }
    }

    private fun updateSources(data: SourceRequest) {
        Platform.runLater {
            logger.info("updating sources")
            data.sources?.let { sourceSelect.items.addAll(it) }
            statusLabel.text = "Status code : ${data.status}"
        }
    }

    private fun updateBreakingNews(data: HeadlineRequest) {
        Platform.runLater {
            logger.info("clearing news view")
            sourceGrid.children.clear()
            logger.info("updating breaking news")
            statusLabel.text = "Status code : ${data.status}"
            if (!data.articles.isNullOrEmpty()) {
                data.articles.withIndex().forEach { (index, item) ->
                    logger.info("updateNews $item")
                    sourceGrid.addRow(index, borderpane {
                        top {
                            label(item.title)
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
                                            find<ArticleViewFx>(mapOf("article" to item)).openWindow(stageStyle = StageStyle.UTILITY)
                                        }
                                    }
                                }
                            }
                        }
                        center {
                            text(item.description)
                        }
                    })
                }
            } else {
                logger.info("no results found")
                sourceGrid.addRow(0, pane {
                    label("No results found")
                })
            }
        }
    }

    init {
        headlineFxController.registerView(this)
        sourceController.registerView(this)

        with(root) {
            prefWidth = 1000.0
            prefHeight = 800.0
        }

        //headlineFxController.findCountries()
        logger.info("all countries names fetched successfully")
        //headlineFxController.findBreakingNews("fr", "general", null, null)
        logger.info("breaking news found for France (fr)")
        //sourceController.findSources(null, null, null, null)
        logger.info("all sources names fetched successfully")
    }

    override fun display() {
        this.display()
    }

    override fun updateNews(data: Any) {
        when (data) {
            is HeadlineRequest -> {
                //logger.info("receive breaking news")
                //updateBreakingNews(data)
            }
            is Array<*> -> {
                data.forEach {
                    //logger.info("received country ${it as Country}")
                    countrySelect.items.add(it as Country)
                }
                Platform.runLater {
                    countrySelect.value = headlineFxController.indexOfAllowedCountryFromAlpha2code("fr")?.let { countrySelect.items.get(it) }
                }
            }
            is SourceRequest -> {
                logger.info("receive sources $data")
                //updateSources(data)
            }
            else -> {
                logger.info("data : $data")
            }
        }
    }
}