package by.varyvoda.bankapp.domain.dependency

import by.varyvoda.bankapp.domain.repository.impl.*
import by.varyvoda.bankapp.domain.repository.connection.ConnectionProvider
import by.varyvoda.bankapp.ui.mode.client.service.ClientModeService

fun registerDependencies() {
    val dependencyProvider = DependencyProvider.get()
    dependencyProvider.register(ConnectionProvider::class.java, ConnectionProvider.INSTANCE)
    dependencyProvider.register(CityRepository::class.java, CityRepository())
    dependencyProvider.register(CountryRepository::class.java, CountryRepository())
    dependencyProvider.register(DisabilityRepository::class.java, DisabilityRepository())
    dependencyProvider.register(MaritalStatusRepository::class.java, MaritalStatusRepository())
    dependencyProvider.register(ClientRepository::class.java, ClientRepository())
    dependencyProvider.register(ClientModeService::class.java, ClientModeService())
}