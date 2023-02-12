package by.varyvoda.bankapp.ui.mode.deposit

import by.varyvoda.bankapp.ui.mode.deposit.component.DepositForm
import by.varyvoda.bankapp.ui.mode.deposit.component.DepositTable
import javafx.geometry.Orientation
import javafx.scene.layout.StackPane
import tornadofx.*

class DepositMode : StackPane() {

    private var form: DepositForm by singleAssign()
    private var table: DepositTable by singleAssign()

    init {
        splitpane(orientation = Orientation.HORIZONTAL) {
            form = DepositForm()
            table = DepositTable()
            addChildIfPossible(form)
            addChildIfPossible(table)
        }
    }
}