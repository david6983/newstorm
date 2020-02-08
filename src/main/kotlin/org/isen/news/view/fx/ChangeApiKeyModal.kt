package org.isen.news.view.fx

import javafx.scene.control.Alert
import javafx.scene.control.TextField
import org.isen.news.ctrl.jfx.TopHeadlineFxController
import tornadofx.*

class ChangeApiKeyModal : Fragment("Change the api key") {
    private val controller: TopHeadlineFxController by inject()

    private var keyField: TextField by singleAssign()

    override val root = form {
        fieldset("Enter the new api key") {
            textfield("") {
                keyField = this
            }
            button("change").action {
                controller.changeApiKey(keyField.text)
                alert(type= Alert.AlertType.CONFIRMATION,header= "api key updated")
                close()
            }
        }
    }
}