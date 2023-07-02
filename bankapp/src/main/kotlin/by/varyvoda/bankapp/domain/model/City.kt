package by.varyvoda.bankapp.domain.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "city")
class City {

    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "name")
    var name: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as City

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return name
    }
}