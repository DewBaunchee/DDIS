package by.varyvoda.bankapp.ui.mode.deposit.component

import by.varyvoda.bankapp.domain.dependency.provide
import by.varyvoda.bankapp.domain.model.Deposit
import by.varyvoda.bankapp.ui.mode.deposit.service.DepositModeService
import by.varyvoda.bankapp.ui.util.observe
import javafx.scene.control.TableView
import tornadofx.*

class DepositTable : TableView<Deposit>() {

    private val service = provide(DepositModeService::class.java)

    init {
        readonlyColumn("Client", Deposit::client)
        readonlyColumn("Program", Deposit::program)
        readonlyColumn("Begin", Deposit::begin)
        readonlyColumn("End", Deposit::end)
        readonlyColumn("Amount", Deposit::amount)
        readonlyColumn("Currency", Deposit::currency)

        items = service.deposits
        selectionModel.selectedItemProperty().observe {
            service.selectedDeposit.value = it
        }
        service.refresh()
    }
}