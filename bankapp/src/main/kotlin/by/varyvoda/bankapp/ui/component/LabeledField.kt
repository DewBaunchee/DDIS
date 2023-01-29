package by.varyvoda.bankapp.ui.component

import javafx.event.EventTarget
import javafx.scene.layout.VBox
import tornadofx.*

fun EventTarget.labeledField(label: String, builder: VBox.() -> Unit): VBox {
    return VBox()
        .apply { label(label) {} }
        .attachTo(this, builder)
}
