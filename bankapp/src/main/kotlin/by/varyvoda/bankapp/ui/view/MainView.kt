package by.varyvoda.bankapp.ui.view

import by.varyvoda.bankapp.ui.mode.AddClientMode
import by.varyvoda.bankapp.ui.mode.Mode
import by.varyvoda.bankapp.ui.util.observe
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Node
import tornadofx.*

class MainView : View("") {

    private val selectedWindow: SimpleObjectProperty<Mode> = SimpleObjectProperty(AddClientMode())

    override fun onDock() {
        super.onDock()
        primaryStage.isMaximized = true
        primaryStage.titleProperty().unbind()
        selectedWindow.observe { primaryStage.title = it.name }
    }

    override val root = hbox {
        vbox {
            button("Add Client") { selectedWindow.value = AddClientMode() }
        }
        stackpane {
            selectedWindow.observe { children.setAll(it as Node) }
        }
    }
}