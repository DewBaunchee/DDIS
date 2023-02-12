package by.varyvoda.bankapp.ui.mode.client.component

import by.varyvoda.bankapp.domain.dependency.provide
import by.varyvoda.bankapp.domain.model.Client
import by.varyvoda.bankapp.ui.mode.client.service.ClientModeService
import javafx.scene.control.TableView
import tornadofx.*

class ClientTable : TableView<Client>() {

    private val clientModeService = provide(ClientModeService::class.java)

    init {
        column("Last Name", Client::lastName)
        column("First Name", Client::firstName)
        column("Patronymic", Client::patronymic)
        column("Birthday", Client::birthday)
        column("Sex", Client::sex)
        column("Passport Series", Client::passportSeries)
        column("Passport Number", Client::passportNumber)
        column("Produced By", Client::producedBy)
        column("Producing Date", Client::producingDate)
        column("Identifier", Client::id)
        column("Birthplace", Client::birthplace)
        column("Residence City", Client::residenceCity)
        column("Residence Address", Client::residenceAddress)
        column("Home Phone Number", Client::homePhoneNumber)
        column("Mobile Phone Number", Client::mobilePhoneNumber)
        column("E-Mail", Client::email)
        column("Marital Status", Client::maritalStatus)
        column("Citizenship", Client::citizenship)
        column("Disability", Client::disability)
        column("Pensioner", Client::pensioner).cellFormat {
            text = if (it) "Yes" else "No"
        }
        column("Month income", Client::monthIncome).cellFormat {
            text = if (it == -1) "" else it.toString()
        }
        column("Conscript", Client::conscript).cellFormat {
            text = if (it) "Yes" else "No"
        }

        columnResizePolicy = UNCONSTRAINED_RESIZE_POLICY

        items = clientModeService.clients
        clientModeService.selectedClient.bind(selectionModel.selectedItemProperty())
        clientModeService.refresh()
    }
}