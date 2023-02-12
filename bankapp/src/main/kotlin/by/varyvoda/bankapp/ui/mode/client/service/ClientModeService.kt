package by.varyvoda.bankapp.ui.mode.client.service

import by.varyvoda.bankapp.domain.dependency.provide
import by.varyvoda.bankapp.domain.model.Client
import by.varyvoda.bankapp.domain.repository.impl.ClientRepository
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class ClientModeService {

    val clients = observableListOf<Client>()
    val selectedClient = SimpleObjectProperty<Client>()

    private val clientRepository = provide(ClientRepository::class.java)

    fun refresh() {
        clients.setAll(clientRepository.getAll())
    }
}