package by.varyvoda.bankapp.domain.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.persistence.*

@Entity(name = "account")
class Account {
    @Transient
    val numberProperty = SimpleStringProperty(this, "number", "")

    @get:Id
    @get:Column(name = "number")
    var number by numberProperty

    @Transient
    val codeProperty = SimpleStringProperty(this, "code", "")

    @get:Column(name = "code")
    var code by codeProperty

    @Transient
    val activeProperty = SimpleBooleanProperty(this, "active", true)

    @get:Column(name = "active")
    var active by activeProperty

    @Transient
    val passiveProperty = SimpleBooleanProperty(this, "passive", true)

    @get:Column(name = "passive")
    var passive by passiveProperty

    @Transient
    val debitProperty = SimpleIntegerProperty(this, "debit", 0)

    @get:Column(name = "debit")
    var debit by debitProperty

    @Transient
    val creditProperty = SimpleIntegerProperty(this, "credit", 0)

    @get:Column(name = "credit")
    var credit by creditProperty

    @Transient
    val balanceProperty = SimpleIntegerProperty(this, "balance", 0)

    @get:Column(name = "balance")
    var balance by balanceProperty

    @Transient
    val currencyProperty = SimpleObjectProperty(this, "currency", Currency())

    @get:JoinColumn(name = "currency")
    var currency by currencyProperty

    @Transient
    val clientProperty = SimpleObjectProperty(this, "client", Client())

    @get:JoinColumn(name = "client")
    var client by clientProperty

    override fun toString(): String {
        return number
    }
}