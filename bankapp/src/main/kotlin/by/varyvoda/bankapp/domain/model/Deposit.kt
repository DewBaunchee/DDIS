package by.varyvoda.bankapp.domain.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate
import java.time.Period

class Deposit {
    val idProperty = SimpleIntegerProperty(this, "id", 0)
    var id by idProperty

    val programProperty = SimpleObjectProperty(this, "program", Program())
    var program by programProperty

    val beginProperty = SimpleObjectProperty(this, "begin", LocalDate.now())
    var begin by beginProperty

    val endProperty = SimpleObjectProperty(this, "end", LocalDate.now())
    var end by endProperty

    val amountProperty = SimpleIntegerProperty(this, "amount", 0)
    var amount by amountProperty

    val currencyProperty = SimpleObjectProperty(this, "currency", Currency())
    var currency by currencyProperty

    val clientProperty = SimpleObjectProperty(this, "client", Client())
    var client by clientProperty

    class Program {
        val idProperty = SimpleIntegerProperty(this, "id", 0)
        var id by idProperty

        val nameProperty = SimpleStringProperty(this, "name", "")
        var name by nameProperty

        val revocableProperty = SimpleBooleanProperty(this, "revocable", false)
        var revocable by revocableProperty

        val periodProperty = SimpleObjectProperty(this, "period", Period.ZERO)
        var period by periodProperty

        val firstPaymentProperty = SimpleIntegerProperty(this, "firstPayment", 0)
        var firstPayment by firstPaymentProperty

        val percentStrategyProperty = SimpleObjectProperty(this, "percentStrategy", Strategy(listOf()))
        var percentStrategy by percentStrategyProperty

        val replenishmentProperty = SimpleStringProperty(this, "replenishment", "")
        var replenishment by replenishmentProperty

        val expenditureOperationsProperty = SimpleStringProperty(this, "expenditureOperations", "")
        var expenditureOperations by expenditureOperationsProperty

        val autoProlongationProperty = SimpleBooleanProperty(this, "autoProlongation", false)
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