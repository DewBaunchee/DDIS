package by.varyvoda.bankapp.ui.mode.client.service

import by.varyvoda.bankapp.domain.model.Client
import by.varyvoda.bankapp.domain.repository.impl.ClientRepository
import javafx.beans.property.SimpleObjectProperty
import org.springframework.stereotype.Service
import tornadofx.*

@Service
class ClientModeService(private val clientRepository: ClientRepository) {

    val clients = observableListOf<Client>()
    val selectedClient = SimpleObjectProperty<Client>()

    fun refresh() {
        clients.setAll(clientRepository.findAll())
    }
}