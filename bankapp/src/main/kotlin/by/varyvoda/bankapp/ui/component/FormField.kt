package by.varyvoda.bankapp.ui.component

import javafx.event.EventTarget
import javafx.scene.layout.HBox
import tornadofx.*

class FormField<F>(label: String, val field: F) : HBox() {

    val label = label(label) { }

    init {
        spacing = 10.0
    }
}

fun <F> EventTarget.formField(label: String, field: F, builder: FormField<F>.() -> Unit): FormField<F> {
    return FormField(label, field).attachTo(this, builder)
}