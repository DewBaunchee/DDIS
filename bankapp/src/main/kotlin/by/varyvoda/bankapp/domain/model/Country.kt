package by.varyvoda.bankapp.domain.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "country")
class Country {
    @Id
    @Column(name = "key", nullable = false)
    var key: String = ""

    @Column(name = "key", nullable = false)
    var name: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Country

        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    override fun toString(): String {
        return name
    }
}