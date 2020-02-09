package org.isen.news.view

import org.apache.logging.log4j.kotlin.Logging
import org.isen.news.ctrl.TopHeadlineController
import org.isen.news.model.data.Article
import org.isen.news.model.data.Category
import org.isen.news.model.data.HeadlineRequest
import java.awt.*
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.text.SimpleDateFormat
import javax.swing.*

/**
 * This class is no longer used because it's a tornadofx application
 */
class TopHeadlineView : INewsView, ActionListener {
    companion object : Logging

    private val frame: JFrame = JFrame()

    private val controller: TopHeadlineController

    private var breakingNews: JPanel = JPanel(BorderLayout())

    constructor(controller: TopHeadlineController) {
        this.frame.contentPane = makeUi()
        this.frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        this.frame.preferredSize = Dimension(800, 700)
        this.frame.title = "The news"
        this.frame.pack()
        this.controller = controller
        this.controller.registerView(this)

        controller.findBreakingNews("fr", null, null, null)
    }

    private fun makeUi() : JPanel {
        val contentPane = JPanel()
        contentPane.layout = BorderLayout()

        contentPane.add(createUiForMenuBar(), BorderLayout.NORTH)
        contentPane.add(createUiForBReakingNewsFilter(), BorderLayout.WEST)
        contentPane.add(createUiForBreakingNews(), BorderLayout.CENTER)

        return contentPane
    }

    private fun createUiForMenuBar(): JMenuBar {
        val menuBar: JMenuBar = JMenuBar()

        val editMenu = JMenu("Edit")

        val apiKeyMenuItem = JMenuItem("Change Api Key")
        val pageSizeMenuItem = JMenuItem("Change page size")

        editMenu.add(apiKeyMenuItem)
        editMenu.add(pageSizeMenuItem)

        menuBar.add(editMenu)

        return menuBar
    }

    private fun createUiForBReakingNewsFilter(): JPanel {
        val filterPanel = JPanel()
        filterPanel.layout = BoxLayout(filterPanel, BoxLayout.PAGE_AXIS)

        val title = JLabel("Filters")
        title.font = Font("Courier", Font.BOLD,18)
        filterPanel.add(title)

        val filterForm = JPanel(GridLayout(3, 2))

        val categoryLabel = JLabel("Category : ")
        filterForm.add(categoryLabel)

        val categorySelect: JComboBox<String> = buildCategorySelector()
        categorySelect.selectedItem = "Any"
        filterForm.add(categorySelect)

        val keywordsLabel = JLabel("Keywords")
        filterForm.add(keywordsLabel)

        val keywordsInput = JTextArea()
        filterForm.add(keywordsInput)

        filterPanel.add(filterForm)
        return filterPanel
    }

    private fun buildCategorySelector(): JComboBox<String> {
        val countrySelect: JComboBox<String> = JComboBox()
        Category.values().toList().forEach {
            countrySelect.addItem(it.toString())
        }

        return countrySelect
    }

    private fun createUiForBreakingNews(): JPanel {
        return breakingNews
    }

    private fun createArticles(data: Article): JPanel {
        val result = JPanel()
        result.background = Color.WHITE
        result.layout = BoxLayout(result, BoxLayout.PAGE_AXIS)

        result.border = BorderFactory.createLineBorder(Color.black)

        val title = JLabel(data.title)
        title.font = Font("Courier", Font.BOLD,18)

        result.add(title)
        result.add(JLabel(data.description))

        val formatter = SimpleDateFormat("dd/MMMMMM/yyyy hh:mm:ss")

        val gridinfo = JPanel(GridLayout(1, 3))
        gridinfo.background = Color.WHITE

        val author = if (data.author != null) {
            "${data.author}"
        } else {
            ""
        }
        gridinfo.add(JLabel(author))
        gridinfo.add(JLabel(formatter.format(data.publishedAt)))
        gridinfo.add(JButton("Open"))
        result.add(gridinfo)
        return result
    }

    private fun updateBreakingNews(data: HeadlineRequest) {
        val paneAllItem = JPanel(GridLayout(data.articles!!.size, 1))

        for (item: Article in data.articles!!) {
            logger.debug("updateNews $item")
            paneAllItem.add(createArticles(item))
        }

        val scrollPane = JScrollPane(paneAllItem)
        breakingNews.add(scrollPane, BorderLayout.CENTER)
        breakingNews.revalidate()
    }

    override fun display() {
        this.frame.isVisible = true
    }

    override fun close() {
        this.frame.isVisible = false
        this.frame.dispose()
    }

    override fun updateNews(data: Any) {
        if (data is HeadlineRequest) {
            logger.info("receive breaking news $data")
            updateBreakingNews(data)
        }
    }

    override fun updateStatusCode(code: Int) {

    }

    override fun actionPerformed(e: ActionEvent?) {

    }
}