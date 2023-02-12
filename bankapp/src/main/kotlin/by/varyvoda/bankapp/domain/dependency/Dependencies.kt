package by.varyvoda.bankapp.domain.dependency

import by.varyvoda.bankapp.domain.repository.connection.ConnectionProvider
import by.varyvoda.bankapp.domain.repository.impl.*
import by.varyvoda.bankapp.ui.mode.client.service.ClientModeService
import by.varyvoda.bankapp.ui.mode.deposit.service.DepositModeService
import by.varyvoda.bankapp.ui.view.service.MainViewService

fun registerDependencies() {
    DependencyProvider.get().apply {
        register(ConnectionProvider.INSTANCE)
        register(CityRepository())
        register(CountryRepository())
        register(DisabilityRepository())
        register(MaritalStatusRepository())
        register(MainViewService())
        register(ClientRepository())
        register(ClientModeService())
        register(DepositProgramRepository())
        register(CurrencyRepository())
        register(DepositRepository())
        register(DepositModeService())
    }
}