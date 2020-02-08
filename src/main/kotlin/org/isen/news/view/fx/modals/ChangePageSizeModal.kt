package org.isen.news.view.fx.modals

import javafx.scene.control.Alert
import javafx.scene.control.TextField
import org.isen.news.ctrl.jfx.TopHeadlineFxController
import tornadofx.*

class ChangePageSizeModal : Fragment("Change the number of article by page") {
    private val controller: TopHeadlineFxController by inject()

    private var pageSizeField: TextField by singleAssign()

    override val root = form {
        fieldset("Enter the new page size (integer") {
            textfield("") {
                pageSizeField = this
            }
            button("change").action {
                val value = pageSizeField.text.toIntOrNull()
                if (value != null) {
                    controller.changePageSize(value)
                } else {
                    alert(type= Alert.AlertType.WARNING,header= "the value given is not an integer")
                }
                alert(type= Alert.AlertType.CONFIRMATION,header= "page size updated")
                close()
            }
        }
    }
}