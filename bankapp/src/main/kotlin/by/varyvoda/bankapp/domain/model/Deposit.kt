package by.varyvoda.bankapp.domain.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate
import java.time.Period
import javax.persistence.*

@Entity(name = "deposit")
class Deposit {
    @Transient
    val idProperty = SimpleIntegerProperty(this, "id", 0)

    @get:Id
    @get:Column(name = "id")
    var id by idProperty

    @Transient
    val programProperty = SimpleObjectProperty(this, "program", Program())

    @get:JoinColumn(name = "program")
    var program by programProperty

    @Transient
    val beginProperty = SimpleObjectProperty(this, "begin", LocalDate.now())

    @get:Column(name = "begin")
    var begin by beginProperty

    @Transient
    val endProperty = SimpleObjectProperty(this, "end", LocalDate.now())

    @get:Column(name = "end")
    var end by endProperty

    @Transient
    val amountProperty = SimpleIntegerProperty(this, "amount", 0)

    @get:Column(name = "amount")
    var amount by amountProperty

    @Transient
    val currencyProperty = SimpleObjectProperty(this, "currency", Currency())

    @get:JoinColumn(name = "currency")
    var currency by currencyProperty

    @Transient
    val clientProperty = SimpleObjectProperty(this, "client", Client())

    @get:JoinColumn(name = "client")
    var client by clientProperty

    class Program {
        @Transient
        val idProperty = SimpleIntegerProperty(this, "id", 0)

        @get:Column(name = "id")
        var id by idProperty

        @Transient
        val nameProperty = SimpleStringProperty(this, "name", "")

        @get:Column(name = "name")
        var name by nameProperty

        @Transient
        val revocableProperty = SimpleBooleanProperty(this, "revocable", false)

        @get:Column(name = "revocable")
        var revocable by revocableProperty

        @Transient
        val periodProperty = SimpleObjectProperty(this, "period", Period.ZERO)

        @get:Column(name = "period")
        var period by periodProperty

        @Transient
        val firstPaymentProperty = SimpleIntegerProperty(this, "firstPayment", 0)

        @get:Column(name = "first_payment")
        var firstPayment by firstPaymentProperty

        @Transient
        val percentStrategyProperty = SimpleObjectProperty(this, "percentStrategy", Strategy(listOf()))

        @get:Column(name = "percent_strategy")
        var percentStrategy by percentStrategyProperty

        @Transient
        val replenishmentProperty = SimpleStringProperty(this, "replenishment", "")

        @get:Column(name = "replenishment")
        var replenishment by replenishmentProperty

        @Transient
        val expenditureOperationsProperty = SimpleStringProperty(this, "expenditureOperations", "")

        @get:Column(name = "expenditure_operations")
        var expenditureOperations by expenditureOperationsProperty

        @Transient
        val autoProlongationProperty = SimpleBooleanProperty(this, "autoProlongation", false)

        @get:Column(name = "auto_prolongation")
        var autoProlongation by autoProlongationProperty

        override fun toString(): String {
            return name
        }
    }

    class Strategy(val entries: List<Entry>) {

        companion object {

            fun parse(string: String): Strategy {
                return Strategy(string.split("|").map { Entry.parse(it) })
            }
        }

        override fun toString(): String {
            return entries.joinToString("|") { it.toString() }
        }

        fun toPrettyString(): String {
            return entries.joinToString(", then ", "First ") { "${it.duration.toString().drop(1)} is ${it.percent}%" }
        }

        class Entry(val duration: Period, val percent: Double) {

            companion object {

                fun parse(string: String): Entry {
                    val parts = string.split("=")
                    return Entry(Period.parse(parts[0]), parts[1].toDouble())
                }
            }

            override fun toString(): String {
                return "${duration.toString().replace(" ", "")}=$percent"
            }
        }
    }
}