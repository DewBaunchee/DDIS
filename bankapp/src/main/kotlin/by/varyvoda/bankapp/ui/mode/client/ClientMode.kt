package by.varyvoda.bankapp.ui.mode.client

import by.varyvoda.bankapp.ui.mode.Mode
import by.varyvoda.bankapp.ui.mode.client.component.ClientForm
import by.varyvoda.bankapp.ui.mode.client.component.ClientTable
import javafx.geometry.Orientation
import javafx.scene.layout.StackPane
import tornadofx.*

class ClientMode : StackPane(), Mode {

    override val name = "Client adding"

    private var form: ClientForm by singleAssign()
    private var table: ClientTable by singleAssign()

    init {
        splitpane(orientation = Orientation.HORIZONTAL) {
            form = ClientForm()
            table = ClientTable()
            addChildIfPossible(form)
            addChildIfPossible(table)
        }
    }
}