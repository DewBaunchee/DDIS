package by.varyvoda.bankapp.ui.mode.client.service

import by.varyvoda.bankapp.domain.dependency.DependencyProvider
import by.varyvoda.bankapp.domain.model.Client
import by.varyvoda.bankapp.domain.repository.impl.ClientRepository
import by.varyvoda.bankapp.ui.mode.client.ClientMode
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class ClientModeService {

    val items = observableListOf<Client>()
    val selectedClient = SimpleObjectProperty<Client>()

    private val clientRepository = DependencyProvider.get().provide(ClientRepository::class.java)

    fun refresh() {
        items.setAll(clientRepository.getAll())
    }
}