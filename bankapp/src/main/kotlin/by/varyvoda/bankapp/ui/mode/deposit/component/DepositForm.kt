package by.varyvoda.bankapp.ui.mode.deposit.component

import by.varyvoda.bankapp.domain.dependency.provide
import by.varyvoda.bankapp.domain.model.Deposit
import by.varyvoda.bankapp.domain.repository.impl.CurrencyRepository
import by.varyvoda.bankapp.domain.repository.impl.DepositProgramRepository
import by.varyvoda.bankapp.domain.repository.impl.DepositRepository
import by.varyvoda.bankapp.ui.component.labeledField
import by.varyvoda.bankapp.ui.mode.deposit.service.DepositModeService
import by.varyvoda.bankapp.ui.util.ChainValidator
import by.varyvoda.bankapp.ui.util.FxValidator
import by.varyvoda.bankapp.ui.util.observe
import javafx.animation.Interpolator
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.*
import java.time.LocalDate

class DepositForm : VBox() {

    private val model = DepositModel(Deposit())

    private val depositRepository = provide(DepositRepository::class.java)
    private val depositProgramRepository = provide(DepositProgramRepository::class.java)
    private val currencyRepository = provide(CurrencyRepository::class.java)

    private val depositModeService = provide(DepositModeService::class.java)

    private val randomizers = mutableListOf<() -> Unit>()

    private val actionResult = SimpleObjectProperty<ValidationMessage>()

    init {
        depositModeService.selectedDeposit.observe {
            model.item = it ?: Deposit()
            model.undecorated()
        }

        val notNull: FxValidator<Any> = {
            if (it == null)
                error("Value is not present")
            else
                success()
        }
        val notEmpty: FxValidator<String> = {
            if (it.isNullOrBlank())
                ValidationMessage("Field is empty", ValidationSeverity.Error)
            else
                ValidationMessage("", ValidationSeverity.Success)
        }
        val lengthIs: (Int) -> FxValidator<String> = { length ->
            {
                if (it!!.length != length)
                    ValidationMessage("Required length is $length", ValidationSeverity.Error)
                else
                    ValidationMessage("", ValidationSeverity.Success)
            }
        }
        val isOnlyText: (String) -> Boolean = { str -> str.all { it.isLetter() || it.isWhitespace() } }

        paddingAll = 15

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
                                ChainValidator<Deposit> {
                                    if (!model.isValid)
                                        ValidationMessage(
                                            "Errors in form!", ValidationSeverity.Error
                                        )
                                    else null
                                }.validate(model.item)

                            if (validation == null || validation.severity == ValidationSeverity.Success) {
                                depositRepository.save(model.item)
                                depositModeService.refresh()
                                actionResult.value = ValidationMessage(
                                    "Client saved", ValidationSeverity.Success
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
                        depositRepository.delete(model.item.id)
                        depositModeService.refresh()
                    }
                }
            }
            hbox {
                styled()
                button("Refresh") {
                    onAction = EventHandler {
                        depositModeService.refresh()
                    }
                }
            }
            hbox {
                styled()
                button("End day") {
                    onAction = EventHandler {
                        depositModeService.endDay()
                    }
                }
            }
        }
        vbox {
            hbox {
                styled()
                labeledField("Program") {
                    combobox(model.program) {
                        items = observableListOf(depositProgramRepository.getAll())

                        selectionModel.selectedItemProperty().observe {
                            if (it.id == 0) {
                                selectionModel.selectFirst()
                                model.program.value = items.first()
                            }
                        }
                    }
                }
            }
            add(DepositProgramComponent(model.program))
        }
        hbox {
            styled()
            labeledField("Client ID") {
                label(model.itemProperty.select { deposit -> deposit.clientProperty.select { it.idProperty } })
            }
            labeledField("Name") {
                label(model.itemProperty.select { deposit -> deposit.clientProperty.select { SimpleStringProperty(it.fullName) } })
            }
        }
        hbox {
            styled()
            labeledField("Begin") {
                datepicker(model.begin) {
                    value = LocalDate.now()
                    isEditable = false
                }
            }
            labeledField("End") {
                textfield(model.end.select { SimpleStringProperty(it.toString()) }) {
                    isEditable = false
                    model.begin.observe {
                        model.end.value = model.program.value.period.addTo(model.begin.value) as LocalDate
                    }
                    model.program.observe {
                        model.end.value = model.program.value.period.addTo(model.begin.value) as LocalDate
                    }
                }
            }
        }
        hbox {
            styled()
            labeledField("Amount") {
                hbox {
                    spacing = 10.0
                    textfield(model.amount).apply {
                        filterInput { it.controlNewText.isInt() }
                    }
                    combobox(model.currency) {
                        items = observableListOf(currencyRepository.getAll())
                        selectionModel.selectFirst()
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

class DepositModel(deposit: Deposit) : ItemViewModel<Deposit>(deposit) {
    val program = bind(Deposit::programProperty)
    val begin = bind(Deposit::beginProperty)
    val end = bind(Deposit::endProperty)
    val amount = bind(Deposit::amountProperty)
    val currency = bind(Deposit::currencyProperty)

    fun undecorated() {
        validate(decorateErrors = false)
    }
}