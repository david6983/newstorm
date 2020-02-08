package org.isen.news.view.fx

import javafx.stage.StageStyle
import tornadofx.*

class NewsMainFxMenuBar : View() {
    override val root = menubar {
        menu("Edit") {
            item("Change API KEY").action {
                find<ChangeApiKeyModal>().openModal(stageStyle = StageStyle.UTILITY)
            }
            item("Change page's size").action {
                find<ChangeApiKeyModal>().openModal(stageStyle = StageStyle.UTILITY)
            }
        }
        menu("View") {
            item("Source view").action {
                find<SearchSourceViewFx>().openWindow(stageStyle = StageStyle.DECORATED)
            }
        }
    }
}