package by.varyvoda.bankapp.ui.view

import by.varyvoda.bankapp.domain.dependency.provide
import by.varyvoda.bankapp.ui.mode.Mode
import by.varyvoda.bankapp.ui.mode.client.ClientMode
import by.varyvoda.bankapp.ui.mode.deposit.DepositMode
import by.varyvoda.bankapp.ui.util.observe
import by.varyvoda.bankapp.ui.view.service.MainViewService
import javafx.scene.control.TabPane
import tornadofx.*

class MainView : View("") {

    private val service = provide(MainViewService::class.java)

    override fun onDock() {
        super.onDock()
        primaryStage.isMaximized = true
        primaryStage.titleProperty().unbind()
        service.selectedMode.observe { primaryStage.title = it.value }
    }

    override val root =
        tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

            tab("Clients") {
                userData = Mode.CLIENTS
                add(ClientMode())
            }
            tab("Deposits") {
                userData = Mode.DEPOSITS
                add(DepositMode())
            }

            selectionModel.select(1)

            selectionModel.selectedItemProperty().observe {
                service.selectMode(it.userData as Mode)
            }
            service.selectedMode.observe {
                tabs.find { tab -> tab.userData as Mode == it }!!.select()
            }
        }
}