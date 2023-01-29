package by.varyvoda.bankapp.domain.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate

class Client {
    val lastNameProperty = SimpleStringProperty(this, "lastName", "")
    var lastName: String by lastNameProperty

    val firstNameProperty = SimpleStringProperty(this, "firstName", "")
    var firstName: String by firstNameProperty

    val patronymicProperty = SimpleStringProperty(this, "patronymic", "")
    var patronymic: String by patronymicProperty

    val birthdayProperty = SimpleObjectProperty(this, "birthday", LocalDate.now())
    var birthday: LocalDate by birthdayProperty

    val sexProperty = SimpleObjectProperty(this, "sex", Sex.MALE)
    var sex: Sex by sexProperty

    val passportSeriesProperty = SimpleStringProperty(this, "passportSeries", "")
    var passportSeries: String by passportSeriesProperty

    val passportNumberProperty = SimpleStringProperty(this, "passportNumber", "")
    var passportNumber: String by passportNumberProperty

    val producedByProperty = SimpleStringProperty(this, "producedBy", "")
    var producedBy: String by producedByProperty

    val producingDateProperty = SimpleObjectProperty(this, "producingDate", LocalDate.now())
    var producingDate: LocalDate by producingDateProperty

    val idProperty = SimpleStringProperty(this, "id", "")
    var id: String by idProperty

    val birthplaceProperty = SimpleStringProperty(this, "birthplace", "")
    var birthplace: String by birthplaceProperty

    val residenceCityProperty = SimpleObjectProperty(this, "residenceCity", City())
    var residenceCity: City by residenceCityProperty

    val residenceAddressProperty = SimpleStringProperty(this, "residenceAddress", "")
    var residenceAddress: String by residenceAddressProperty

    val homePhoneNumberProperty = SimpleStringProperty(this, "homePhoneNumber", null)
    var homePhoneNumber: String? by homePhoneNumberProperty

    val mobilePhoneNumberProperty = SimpleStringProperty(this, "mobilePhoneNumber", null)
    var mobilePhoneNumber: String? by mobilePhoneNumberProperty

    val emailProperty = SimpleStringProperty(this, "email", null)
    var email: String? by emailProperty

    val maritalStatusProperty = SimpleObjectProperty(this, "maritalStatus", MaritalStatus())
    var maritalStatus: MaritalStatus by maritalStatusProperty

    val citizenshipProperty = SimpleObjectProperty(this, "citizenship", Country())
    var citizenship: Country by citizenshipProperty

    val disabilityProperty = SimpleObjectProperty(this, "disability", Disability())
    var disability: Disability by disabilityProperty

    val pensionerProperty = SimpleBooleanProperty(this, "pensioner", false)
    var pensioner: Boolean by pensionerProperty

    val monthIncomeProperty = SimpleIntegerProperty(this, "monthIncome", -1)
    var monthIncome: Int by monthIncomeProperty

    val conscriptProperty = SimpleBooleanProperty(this, "conscript", true)
    var conscript: Boolean by conscriptProperty
}