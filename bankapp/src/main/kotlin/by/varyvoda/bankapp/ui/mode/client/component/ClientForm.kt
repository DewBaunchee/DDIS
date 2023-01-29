package by.varyvoda.bankapp.ui.mode.client.component

import by.varyvoda.bankapp.domain.dependency.DependencyProvider
import by.varyvoda.bankapp.domain.model.Client
import by.varyvoda.bankapp.domain.model.Sex
import by.varyvoda.bankapp.domain.repository.impl.*
import by.varyvoda.bankapp.ui.component.labeledField
import by.varyvoda.bankapp.ui.mode.client.service.ClientModeService
import by.varyvoda.bankapp.ui.util.ChainValidator
import by.varyvoda.bankapp.ui.util.observe
import by.varyvoda.bankapp.ui.util.then
import com.mifmif.common.regex.Generex
import javafx.animation.Interpolator
import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.HBox
import tornadofx.*
import java.time.LocalDate
import java.util.regex.Pattern
import kotlin.random.Random
import kotlin.random.nextInt

typealias Validator<T> = ValidationContext.(T?) -> ValidationMessage?

class ClientForm : HBox() {

    private val model = ClientModel(Client())

    private val clientRepository = DependencyProvider.get().provide(ClientRepository::class.java)
    private val cityRepository = DependencyProvider.get().provide(CityRepository::class.java)
    private val maritalStatusRepository = DependencyProvider.get().provide(MaritalStatusRepository::class.java)
    private val countryRepository = DependencyProvider.get().provide(CountryRepository::class.java)
    private val disabilityRepository = DependencyProvider.get().provide(DisabilityRepository::class.java)

    private val clientModeService = DependencyProvider.get().provide(ClientModeService::class.java)

    private val randomizers = mutableListOf<() -> Unit>()

    private val actionResult = SimpleObjectProperty<ValidationMessage>()

    init {
        clientModeService.selectedClient.observe {
            model.item = it ?: Client()
            model.resetValidation()
        }

        val notNull: Validator<Any> = {
            if (it == null)
                error("Value is not present")
            else
                success()
        }
        val notEmpty: Validator<String> = {
            if (it.isNullOrBlank())
                ValidationMessage("Field is empty", ValidationSeverity.Error)
            else
                ValidationMessage("", ValidationSeverity.Success)
        }
        val lengthIs: (Int) -> Validator<String> = { length ->
            {
                if (it!!.length != length)
                    ValidationMessage("Required length is $length", ValidationSeverity.Error)
                else
                    ValidationMessage("", ValidationSeverity.Success)
            }
        }
        val isOnlyText: (String) -> Boolean = { str -> str.all { it.isLetter() || it.isWhitespace() } }

        paddingAll = 15

        vbox {
            hbox {
                styled()
                hbox {
                    styled()
                    button("Random") {
                        onAction = EventHandler {
                            randomizers.forEach { it() }
                        }
                    }
                }
                hbox {
                    styled()
                    button("Save") {
                        onAction = EventHandler {
                            model.commit {
                                val validation =
                                    ChainValidator<Client> {
                                        if (!model.isValid)
                                            ValidationMessage(
                                                "Errors in form!", ValidationSeverity.Error
                                            )
                                        else null
                                    }.then {
                                        if (clientRepository.get(it.id) != null)
                                            ValidationMessage(
                                                "Client with such id is already saved!", ValidationSeverity.Error
                                            )
                                        else null
                                    }.then {
                                        if (clientRepository.getByPassport(
                                                it.passportSeries,
                                                it.passportNumber
                                            ) != null
                                        ) ValidationMessage(
                                            "Client with such passport is already saved!",
                                            ValidationSeverity.Error
                                        )
                                        else null
                                    }.validate(model.item)

                                if (validation == null || validation.severity == ValidationSeverity.Success) {
                                    clientRepository.save(model.item)
                                    clientModeService.refresh()
                                    actionResult.value = ValidationMessage(
                                        "Client saved", ValidationSeverity.Success
                                    )
                                } else {
                                    actionResult.value = validation
                                }
                            }
                        }
                    }
                    button("Update") {
                        onAction = EventHandler {
                            model.commit {
                                val validation =
                                    ChainValidator<Client> {
                                        if (!model.isValid)
                                            ValidationMessage(
                                                "Errors in form!", ValidationSeverity.Error
                                            )
                                        else null
                                    }.then {
                                        if (clientRepository.get(it.id) == null)
                                            ValidationMessage(
                                                "Client with such id is not found for update!", ValidationSeverity.Error
                                            )
                                        else null
                                    }.then { client ->
                                        if (
                                            clientRepository.getByPassport(
                                                client.passportSeries,
                                                client.passportNumber
                                            ).let { it != null && it.id != client.id }
                                        ) ValidationMessage(
                                            "Client with such passport is already saved!",
                                            ValidationSeverity.Error
                                        )
                                        else null
                                    }.validate(model.item)

                                if (validation == null || validation.severity == ValidationSeverity.Success) {
                                    clientRepository.update(model.item)
                                    clientModeService.refresh()
                                    actionResult.value = ValidationMessage(
                                        "Client updated", ValidationSeverity.Success
                                    )
                                } else {
                                    actionResult.value = validation
                                }
                            }
                        }
                    }
                    button("Cancel") {
                        onAction = EventHandler {
                            model.rollback()
                        }
                    }
                }
                hbox {
                    styled()
                    button("Delete") {
                        onAction = EventHandler {
                            clientRepository.delete(model.item.id)
                            clientModeService.refresh()
                        }
                    }
                }
                hbox {
                    styled()
                    button("Refresh") {
                        onAction = EventHandler {
                            clientModeService.refresh()
                        }
                    }
                }
                hbox {
                    styled()
                    val label = label {
                        style {
                            borderRadius += box(5.px)
                            backgroundRadius += box(5.px)
                            padding = box(5.px)
                        }
                    }
                    actionResult.addListener { _, _, new ->
                        label.text = new.message

                        label.style(append = true) {
                            val color =
                                when (new.severity) {
                                    ValidationSeverity.Success -> "green"
                                    ValidationSeverity.Error -> "red"
                                    else -> "black"
                                }
                            borderColor += box(c(color))
                            backgroundColor += c(color, 0.2)
                        }
                        label.opacity = 1.0
                        runLater(2.0.seconds) {
                            sequentialTransition(true) {
                                timeline {
                                    keyframe(1.0.seconds) {
                                        keyvalue(label.opacityProperty(), 0.0, Interpolator.EASE_BOTH)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            hbox {
                styled()
                labeledField("Last Name") {
                    textfield(model.lastName).apply {
                        filterInput { isOnlyText(it.controlNewText) }
                        validator(ValidationTrigger.OnBlur, notNull.then(notEmpty))
                        val variants = listOf("Varyvoda", "Petrov", "Smith", "Ritter", "Savitzkiy")
                        randomizers.add {
                            model.lastName.value = variants.random()
                        }
                    }
                }
                labeledField("First Name") {
                    textfield(model.firstName).apply {
                        filterInput { isOnlyText(it.controlNewText) }
                        validator(ValidationTrigger.OnBlur, notNull.then(notEmpty))
                        val variants = listOf("Matvey", "Dmitriy", "John", "Scott", "Vladimir")
                        randomizers.add {
                            model.firstName.value = variants.random()
                        }
                    }
                }
                labeledField("Patronymic") {
                    textfield(model.patronymic).apply {
                        filterInput { isOnlyText(it.controlNewText) }
                        validator(ValidationTrigger.OnBlur, notNull)
                        val variants = listOf("Alekseevich", "Alexandrovich", "", "", "Nikolaevich")
                        randomizers.add {
                            model.patronymic.value = variants.random()
                        }
                    }
                }
                labeledField("") {
                    hbox {
                        styled()
                        val toggleGroup = ToggleGroup()
                        val male = radiobutton(Sex.MALE.label, toggleGroup) {
                            onAction = EventHandler { model.sex.value = Sex.MALE }
                        }
                        val female = radiobutton(Sex.FEMALE.label, toggleGroup) {
                            onAction = EventHandler { model.sex.value = Sex.FEMALE }
                        }
                        model.sex.observe {
                            if (it == Sex.MALE) male.isSelected = true
                            if (it == Sex.FEMALE) female.isSelected = true
                        }
                    }
                }
            }
            hbox {
                styled()
                labeledField("Birthday") {
                    datepicker(model.birthday) {
                        isEditable = false
                    }
                    randomizers.add {
                        model.birthday.value = LocalDate.now()
                    }
                }
                labeledField("Birthplace") {
                    textfield(model.birthplace).apply {
                        validator(ValidationTrigger.OnBlur, notNull.then(notEmpty))
                        val variants = listOf("Mars", "Earth", "Jupiter", "Uranus", "Pluto")
                        randomizers.add {
                            model.birthplace.value = variants.random()
                        }
                    }
                }
            }
            hbox {
                styled()
                labeledField("Passport series") {
                    textfield(model.passportSeries).apply {
                        validator(
                            ValidationTrigger.OnBlur,
                            notNull.then(notEmpty).then(lengthIs(2))
                        )
                        val variants = listOf("MC", "MR", "MW", "MK", "MG")
                        randomizers.add {
                            model.passportSeries.value = variants.random()
                        }
                    }
                }
                labeledField("Passport number") {
                    textfield(model.passportNumber).apply {
                        filterInput { it.controlNewText.isInt() }
                        validator(
                            ValidationTrigger.OnBlur,
                            notNull.then(notEmpty).then(lengthIs(7))
                        )
                        randomizers.add {
                            model.passportNumber.value = Random.nextInt(1_000_000..9_999_999).toString()
                        }
                    }
                }
                labeledField("Identifier") {
                    textfield(model.id).apply {
                        val regexString = "[1-6][0-9]{6}[ABCKEMH][0-9]{3}PB[0-9]"
                        val regex = Pattern.compile(regexString)
                        validator(
                            ValidationTrigger.OnChange(100),
                            notNull.then(notEmpty).then {
                                if (regex.matcher(it!!).matches())
                                    success()
                                else
                                    error("Identifier is not correct")
                            }
                        )
                        randomizers.add {
                            model.id.value = Generex(regexString).random()
                        }
                    }
                }
            }
            hbox {
                styled()
                labeledField("Produced by") {
                    textfield(model.producedBy).apply {
                        validator(ValidationTrigger.OnBlur, notNull.then(notEmpty))
                        val variants = listOf("NYPD", "LAPD", "RUVD")
                        randomizers.add {
                            model.producedBy.value = variants.random()
                        }
                    }
                }
                labeledField("Producing date") {
                    datepicker(model.producingDate) {
                        isEditable = false
                        randomizers.add {
                            model.producingDate.value = LocalDate.now()
                        }
                    }
                }
            }
            hbox {
                styled()
                labeledField("Residence City") {
                    combobox(model.residenceCity) {
                        items = observableListOf(cityRepository.getAll())
                        validator(ValidationTrigger.OnBlur, notNull.then {
                            if (it!!.id == 0) return@then error("Field is required")
                            return@then success()
                        })
                        randomizers.add {
                            model.residenceCity.value = items.random()
                        }
                    }
                }
                labeledField("Residence Address") {
                    textfield(model.residenceAddress) {
                        validator(
                            ValidationTrigger.OnBlur,
                            notNull.then(notEmpty)
                        )
                        val variants = listOf("Nowhere", "Anywhere", "Somewhere")
                        randomizers.add {
                            model.residenceAddress.value = variants.random()
                        }
                    }
                }
            }
            hbox {
                styled()
                labeledField("Home Phone Number") {
                    textfield(model.homePhoneNumber) {
                        val regex = Regex(
                            "^\\s*[0-9]{3}\\s*-?\\s*[0-9]{3}\\s*$"
                        )
                        promptText = "XXX-XXX"
                        validator(
                            ValidationTrigger.OnBlur,
                        ) {
                            if (!it.isNullOrBlank() && !regex.matches(it))
                                ValidationMessage(
                                    "Phone number doesn't match to pattern! $promptText",
                                    ValidationSeverity.Error
                                )
                            else null
                        }
                    }
                }
                labeledField("Mobile Phone Number") {
                    textfield(model.mobilePhoneNumber) {
                        val regex = Regex(
                            "\\s*\\+375\\s*-?\\s*\\(?((2([59]))|(33)|(44))\\)?\\s*-?\\s*[0-9]{3}\\s*-?\\s*[0-9]{4}"
                        )
                        promptText = "+375-(XX)-XXX-XXXX"
                        validator(
                            ValidationTrigger.OnBlur
                        ) {
                            if (!it.isNullOrBlank() && !regex.matches(it))
                                ValidationMessage(
                                    "Phone number doesn't match to pattern! $promptText",
                                    ValidationSeverity.Error
                                )
                            else null
                        }
                    }
                }
                labeledField("E-Mail") {
                    textfield(model.email)
                }
            }
            hbox {
                styled()
                labeledField("Marital Status") {
                    combobox(model.maritalStatus) {
                        items = observableListOf(maritalStatusRepository.getAll())
                        validator(ValidationTrigger.OnBlur, notNull.then {
                            if (it!!.id == 0) error("Field is required")
                            else success()
                        })
                        randomizers.add {
                            model.maritalStatus.value = items.random()
                        }
                    }
                }
                labeledField("Citizenship") {
                    combobox(model.citizenship) {
                        items = observableListOf(countryRepository.getAll())
                        validator(ValidationTrigger.OnBlur, notNull.then {
                            if (it!!.key == "") error("Field is required")
                            else success()
                        })
                        randomizers.add {
                            model.citizenship.value = items.random()
                        }
                    }
                }
                labeledField("Disability") {
                    combobox(model.disability) {
                        items = observableListOf(disabilityRepository.getAll())
                        validator(ValidationTrigger.OnBlur, notNull.then {
                            if (it!!.id == 0) error("Field is required")
                            else success()
                        })
                        randomizers.add {
                            model.disability.value = items.random()
                        }
                    }
                }
            }
            hbox {
                styled()
                labeledField("Month Income") {
                    textfield(model.monthIncome).apply {
                        filterInput { it.controlNewText.isInt() }
                    }
                }
                labeledField("") {
                    hbox {
                        checkbox("Pensioner", model.pensioner)
                        checkbox("Conscript", model.conscript)
                    }
                }
            }
        }
    }

    private fun HBox.styled() {
        alignment = Pos.CENTER_LEFT
        spacing = 5.0
        paddingAll = 3
    }
}

class ClientModel(client: Client) : ItemViewModel<Client>(client) {
    val lastName = bind(Client::lastNameProperty)
    val firstName = bind(Client::firstNameProperty)
    val patronymic = bind(Client::patronymicProperty)
    val birthday = bind(Client::birthdayProperty)
    val sex = bind(Client::sexProperty)
    val passportSeries = bind(Client::passportSeriesProperty)
    val passportNumber = bind(Client::passportNumberProperty)
    val producedBy = bind(Client::producedByProperty)
    val producingDate = bind(Client::producingDateProperty)
    val id = bind(Client::idProperty)
    val birthplace = bind(Client::birthplaceProperty)
    val residenceCity = bind(Client::residenceCityProperty)
    val residenceAddress = bind(Client::residenceAddressProperty)
    val homePhoneNumber = bind(Client::homePhoneNumberProperty)
    val mobilePhoneNumber = bind(Client::mobilePhoneNumberProperty)
    val email = bind(Client::emailProperty)
    val maritalStatus = bind(Client::maritalStatusProperty)
    val citizenship = bind(Client::citizenshipProperty)
    val disability = bind(Client::disabilityProperty)
    val pensioner = bind(Client::pensionerProperty)
    val monthIncome = bind(Client::monthIncomeProperty)
    val conscript = bind(Client::conscriptProperty)

    override fun onCommit() {
        item.let {
            if (homePhoneNumber.value.isNullOrBlank()) homePhoneNumber.value = null
            if (mobilePhoneNumber.value.isNullOrBlank()) mobilePhoneNumber.value = null
        }
    }

    fun resetValidation() {
    }
}