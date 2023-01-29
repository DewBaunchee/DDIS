package by.varyvoda.bankapp.ui.util

import javafx.beans.property.ReadOnlyObjectProperty
import javafx.beans.property.ReadOnlyProperty
import tornadofx.*

fun <T> ReadOnlyObjectProperty<T>.observe(observer: (T) -> Unit) {
    observer(value)
    addListener { _, _, new -> observer(new) }
}

fun <T> ReadOnlyProperty<T>.observe(observer: (T) -> Unit) {
    observer(value)
    addListener { _, _, new -> observer(new) }
}

fun <T, NextT : T> (ValidationContext.(T?) -> ValidationMessage?).then(
    validator: ValidationContext.(NextT?) -> ValidationMessage?
): ValidationContext.(NextT?) -> ValidationMessage? =
    chain@{ value ->
        val result = this@then(value)
        if (result?.severity != ValidationSeverity.Success) return@chain result
        return@chain validator(value)
    }