package by.varyvoda.bankapp.ui.view

import by.varyvoda.bankapp.ui.mode.client.ClientMode
import by.varyvoda.bankapp.ui.util.observe
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TabPane
import tornadofx.*

class MainView : View("") {

    private val selectedTab = SimpleStringProperty("")

    override fun onDock() {
        super.onDock()
        primaryStage.isMaximized = true
        primaryStage.titleProperty().unbind()
        selectedTab.observe { primaryStage.title = it }
    }

    override val root =
        tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab("Add Client") {
                add(ClientMode())
            }
            selectionModel.selectedItemProperty().observe {
                selectedTab.value = it.text
            }
        }
}