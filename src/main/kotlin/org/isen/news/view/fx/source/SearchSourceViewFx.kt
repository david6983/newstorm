package org.isen.news.view.fx.source

import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.ctrl.jfx.SourceFxController
import org.isen.news.ctrl.jfx.TopHeadlineFxController
import org.isen.news.model.data.*
import org.isen.news.view.INewsView
import org.isen.news.view.fx.topheadline.TopHeadlineViewFx
import tornadofx.*

class SearchSourceViewFx : View("Search a source"), INewsView {
    companion object : Logging

    private val headlineFxController: TopHeadlineFxController by inject()
    private val sourceController: SourceFxController by inject()

    private val categories = Category.values().toList().asObservable()

    private var categorySelect: ComboBox<Category> by singleAssign()
    private var countrySelect: ComboBox<Country> by singleAssign()

    private var statusLabel: Label by singleAssign()

    private var filterDrawer: Drawer by singleAssign()

    private var sourceGrid: GridPane by singleAssign()

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
                                    field("Category") {
                                        combobox<Category> {
                                            categorySelect = this
                                            items = categories
                                            value = Category.GENERAL
                                            this.valueProperty().onChange {
                                                sourceController.findSources(
                                                        categorySelect.selectedItem?.title,
                                                        null,
                                                        countrySelect.selectedItem?.alpha2Code
                                                )
                                            }
                                        }
                                    }
                                    field("Country") {
                                        combobox<Country> {
                                            countrySelect = this
                                            prefWidth = 120.0
                                            this.valueProperty().onChange {
                                                sourceController.findSources(
                                                        categorySelect.selectedItem?.title,
                                                        null,
                                                        countrySelect.selectedItem?.alpha2Code
                                                )
                                            }
                                        }
                                    }
                                    button("Filter").action {
                                        sourceController.findSources(
                                                categorySelect.selectedItem?.title,
                                                null,
                                                countrySelect.selectedItem?.alpha2Code
                                        )
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
                    hbox {
                        label("Results") {
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
                            sourceGrid = this
                        }
                    }
                }
            }
        }
        bottom {
            label() {
                statusLabel = this
                paddingAll = 10
            }
        }
    }

    private fun updateSources(data: SourceRequest) {
        Platform.runLater {
            logger.info("clearing news view")
            sourceGrid.children.clear()
            logger.info("updating sources")
            if (!data.sources.isNullOrEmpty() && data.status == "ok") {
                data.sources.withIndex().forEach { (index, item) ->
                    logger.info("updateNews $item")
                    sourceGrid.addRow(index, borderpane {
                        top {
                            hbox {
                                label(item.name) {
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
                                button("Official website").action {
                                    hostServices.showDocument(item.url)
                                }
                                alignment = Pos.CENTER
                                paddingAll = 15
                            }
                        }
                        center {
                            vbox {
                                hbox {
                                    label(item.description) {
                                        paddingAll = 15
                                        style {
                                            fontWeight = FontWeight.BOLD
                                        }
                                    }
                                    alignment = Pos.CENTER
                                }
                                vbox {
                                    label("category : ${item.category}")
                                    label("language : ${item.language}")
                                    label("country : ${item.country}")
                                    paddingAll = 15
                                    style {
                                        fontSize = 14.px
                                    }
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
                sourceGrid.addRow(0, pane {
                    label("No results found")
                })
            }
        }
    }

    override fun updateStatusCode(code: Int) {
        logger.info("receive error code $code")
        Platform.runLater {
            statusLabel.text = "Status code : " + when(code) {
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
            prefWidth = 1000.0
            prefHeight = 800.0
        }

        headlineFxController.findCountries()
        logger.info("all countries names fetched successfully")
        sourceController.findSources(null, null, null)
        logger.info("all sources names fetched successfully")
    }

    override fun display() {
        this.display()
    }

    override fun updateNews(data: Any) {
        when (data) {
            is Array<*> -> {
                data.forEach {
                    countrySelect.items.add(it as Country)
                }
                Platform.runLater {
                    countrySelect.value = headlineFxController
                            .indexFromCode("fr")?.let {
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