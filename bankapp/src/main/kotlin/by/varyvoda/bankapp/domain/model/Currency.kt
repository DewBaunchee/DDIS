package by.varyvoda.bankapp.domain.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Currency {
    val keyProperty = SimpleStringProperty(this, "key", "")
    var key by keyProperty

    override fun toString(): String {
        return key
    }
}