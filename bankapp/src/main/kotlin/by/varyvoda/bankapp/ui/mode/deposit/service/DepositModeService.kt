package by.varyvoda.bankapp.ui.mode.deposit.service

import by.varyvoda.bankapp.domain.model.Deposit
import by.varyvoda.bankapp.domain.repository.impl.ClientRepository
import by.varyvoda.bankapp.domain.repository.impl.DepositRepository
import by.varyvoda.bankapp.ui.mode.Mode
import by.varyvoda.bankapp.ui.view.service.MainViewService
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class DepositModeService(
    private val depositRepository: DepositRepository,
    private val clientRepository: ClientRepository,
    private val mainViewService: MainViewService
) {

    val deposits = observableListOf<Deposit>()
    val selectedDeposit = SimpleObjectProperty<Deposit>()

    fun refresh() {
        deposits.setAll(depositRepository.findAll())
    }

    fun newDeposit(clientId: String) {
        val savedClient = clientRepository.getOne(clientId) ?: return

        selectedDeposit.value = Deposit().apply {
            client = savedClient
        }
        mainViewService.selectMode(Mode.DEPOSITS)
    }

    fun endDay() {

    }
}