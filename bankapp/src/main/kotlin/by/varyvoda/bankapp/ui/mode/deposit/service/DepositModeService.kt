package by.varyvoda.bankapp.ui.mode.deposit.service

import by.varyvoda.bankapp.domain.dependency.provide
import by.varyvoda.bankapp.domain.model.Deposit
import by.varyvoda.bankapp.domain.repository.impl.ClientRepository
import by.varyvoda.bankapp.domain.repository.impl.DepositRepository
import by.varyvoda.bankapp.ui.mode.Mode
import by.varyvoda.bankapp.ui.view.service.MainViewService
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class DepositModeService {

    val deposits = observableListOf<Deposit>()
    val selectedDeposit = SimpleObjectProperty<Deposit>()

    private val depositRepository = provide(DepositRepository::class.java)
    private val clientRepository = provide(ClientRepository::class.java)
    private val mainViewService = provide(MainViewService::class.java)

    fun refresh() {
        deposits.setAll(depositRepository.getAll())
    }

    fun newDeposit(clientId: String) {
        val savedClient = clientRepository.get(clientId) ?: return

        selectedDeposit.value = Deposit().apply {
            client = savedClient
        }
        mainViewService.selectMode(Mode.DEPOSITS)
    }

    fun endDay() {

    }
}