package by.varyvoda.bankapp.ui.mode.deposit.component

import by.varyvoda.bankapp.domain.model.Deposit
import by.varyvoda.bankapp.ui.util.observe
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import tornadofx.*

class DepositProgramComponent(program: SimpleObjectProperty<Deposit.Program>) : StackPane() {

    val programProperty = SimpleObjectProperty<Deposit.Program>()
    var program by programProperty

    init {
        program.observe { this.program = it }
        paddingAll = 30
        gridpane {
            hgap = 30.0
            programProperty.observe {
                clear()

                if (it == null) return@observe

                listOf(
                    "Name" to it.name,
                    "Revocable" to if (it.revocable) "Yes" else "No",
                    "Period" to it.period.toString().drop(1),
                    "First payment (BYN)" to it.firstPayment.toString(),
                    "Percent strategy" to it.percentStrategy.toPrettyString(),
                    "Replenishment" to it.replenishment,
                    "Expenditure operations" to it.expenditureOperations,
                    "Auto prolongation" to if (it.autoProlongation) "Yes" else "No"
                ).forEachIndexed { index, pair ->
                    add(Label(pair.first), 0, index)
                    add(Label(pair.second), 1, index)
                }
            }
        }
    }
}