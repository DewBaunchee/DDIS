package by.varyvoda.bankapp.domain.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "client")
class Client {
    @get:Transient
    val fullName get() = "$lastName $firstName $patronymic"

    @Transient
    val lastNameProperty = SimpleStringProperty(this, "lastName", "")

    @get:Column(name = "last_name", nullable = false)
    var lastName: String by lastNameProperty

    @Transient
    val firstNameProperty = SimpleStringProperty(this, "firstName", "")

    @get:Column(name = "first_name", nullable = false)
    var firstName: String by firstNameProperty

    @Transient
    val patronymicProperty = SimpleStringProperty(this, "patronymic", "")

    @get:Column(name = "patronymic", nullable = false)
    var patronymic: String by patronymicProperty

    @Transient
    val birthdayProperty = SimpleObjectProperty(this, "birthday", LocalDate.now())

    @get:Column(name = "birthday", nullable = false)
    var birthday: LocalDate by birthdayProperty

    @Transient
    val sexProperty = SimpleObjectProperty(this, "sex", Sex.MALE)

    @get:Column(name = "sex", nullable = false)
    @get:Enumerated(EnumType.STRING)
    var sex: Sex by sexProperty

    @Transient
    val passportSeriesProperty = SimpleStringProperty(this, "passportSeries", "")

    @get:Column(name = "passport_series", nullable = false)
    var passportSeries: String by passportSeriesProperty

    @Transient
    val passportNumberProperty = SimpleStringProperty(this, "passportNumber", "")

    @get:Column(name = "passport_number", nullable = false)
    var passportNumber: String by passportNumberProperty

    @Transient
    val producedByProperty = SimpleStringProperty(this, "producedBy", "")

    @get:Column(name = "produced_by", nullable = false)
    var producedBy: String by producedByProperty

    @Transient
    val producingDateProperty = SimpleObjectProperty(this, "producingDate", LocalDate.now())

    @get:Column(name = "producing_date", nullable = false)
    var producingDate: LocalDate by producingDateProperty

    @Transient
    val idProperty = SimpleStringProperty(this, "id", "")

    @get:Id
    @get:Column(name = "id", nullable = false)
    var id: String by idProperty

    @Transient
    val birthplaceProperty = SimpleStringProperty(this, "birthplace", "")

    @get:Column(name = "birthplace", nullable = false)
    var birthplace: String by birthplaceProperty

    @Transient
    val residenceCityProperty = SimpleObjectProperty(this, "residenceCity", City())

    @get:JoinColumn(name = "residence_city", nullable = false)
    var residenceCity: City by residenceCityProperty

    @Transient
    val residenceAddressProperty = SimpleStringProperty(this, "residenceAddress", "")

    @get:Column(name = "residence_address", nullable = false)
    var residenceAddress: String by residenceAddressProperty

    @Transient
    val homePhoneNumberProperty = SimpleStringProperty(this, "homePhoneNumber", null)

    @get:Column(name = "home_phone_number")
    var homePhoneNumber: String? by homePhoneNumberProperty

    @Transient
    val mobilePhoneNumberProperty = SimpleStringProperty(this, "mobilePhoneNumber", null)

    @get:Column(name = "mobile_phone_number")
    var mobilePhoneNumber: String? by mobilePhoneNumberProperty

    @Transient
    val emailProperty = SimpleStringProperty(this, "email", null)

    @get:Column(name = "email")
    var email: String? by emailProperty

    @Transient
    val maritalStatusProperty = SimpleObjectProperty(this, "maritalStatus", MaritalStatus())

    @get:JoinColumn(name = "marital_status", nullable = false)
    var maritalStatus: MaritalStatus by maritalStatusProperty

    @Transient
    val citizenshipProperty = SimpleObjectProperty(this, "citizenship", Country())

    @get:JoinColumn(name = "citizenship", nullable = false)
    var citizenship: Country by citizenshipProperty

    @Transient
    val disabilityProperty = SimpleObjectProperty(this, "disability", Disability())

    @get:JoinColumn(name = "disability", nullable = false)
    var disability: Disability by disabilityProperty

    @Transient
    val pensionerProperty = SimpleBooleanProperty(this, "pensioner", false)

    @get:Column(name = "pensioner", nullable = false)
    var pensioner: Boolean by pensionerProperty

    @Transient
    val monthIncomeProperty = SimpleIntegerProperty(this, "monthIncome", -1)

    @get:Column(name = "month_income")
    var monthIncome: Int by monthIncomeProperty

    @Transient
    val conscriptProperty = SimpleBooleanProperty(this, "conscript", true)

    @get:Column(name = "conscript", nullable = false)
    var conscript: Boolean by conscriptProperty

    override fun toString(): String {
        return id
    }
}