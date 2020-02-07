package org.isen.news.view.fx

import javafx.stage.StageStyle
import tornadofx.*

class NewsMainFxMenuBar : View() {
    override val root = menubar {
        menu("Edit") {
            item("Change API KEY")
            item("Change page's size")
        }
        menu("Views") {
            item("Find sources").action {
                find<SearchSourceViewFx>().openWindow(StageStyle.UTILITY)
            }
            //item("Advanced search")
        }
    }
}