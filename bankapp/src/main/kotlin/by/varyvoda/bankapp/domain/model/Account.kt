package by.varyvoda.bankapp.domain.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Account {
    val numberProperty = SimpleStringProperty(this, "number", "")
    var number by numberProperty

    val codeProperty = SimpleStringProperty(this, "code", "")
    var code by codeProperty

    val activeProperty = SimpleBooleanProperty(this, "active", true)
    var active by activeProperty

    val passiveProperty = SimpleBooleanProperty(this, "passive", true)
    var passive by passiveProperty

    val debitProperty = SimpleIntegerProperty(this, "debit", 0)
    var debit by debitProperty

    val creditProperty = SimpleIntegerProperty(this, "credit", 0)
    var credit by creditProperty

    val balanceProperty = SimpleIntegerProperty(this, "balance", 0)
    var balance by balanceProperty

    val currencyProperty = SimpleObjectProperty(this, "currency", Currency())
    var currency by currencyProperty

    val clientProperty = SimpleObjectProperty(this, "client", Client())
    var client by clientProperty

    override fun toString(): String {
        return number
    }
}