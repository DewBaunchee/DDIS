package by.varyvoda.bankapp

import by.varyvoda.bankapp.domain.dependency.registerDependencies
import by.varyvoda.bankapp.ui.view.MainView
import tornadofx.*

class BankApp : App(MainView::class)

fun main() {
    registerDependencies()
    launch<BankApp>()
}