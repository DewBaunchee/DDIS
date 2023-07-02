package by.varyvoda.bankapp.domain.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Transient

@Entity(name = "currency")
class Currency {
    @Transient
    val keyProperty = SimpleStringProperty(this, "key", "")

    @get:Id
    @get:Column(name = "key", nullable = false)
    var key: String by keyProperty

    override fun toString(): String {
        return key
    }
}