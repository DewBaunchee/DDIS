package by.varyvoda.bankapp.ui.mode

import by.varyvoda.bankapp.domain.dependency.DependencyProvider
import by.varyvoda.bankapp.domain.repository.impl.ClientRepository
import by.varyvoda.bankapp.ui.component.formField
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import tornadofx.*

class AddClientMode : HBox(), Mode {

    override val name = "Client adding"

    private val clientRepository = DependencyProvider.get().provide(ClientRepository::class.java)

    init {
        vbox {
            hbox {
                formField("Last Name", TextField()) {  }
            }
        }
        vbox {

        }
    }
}