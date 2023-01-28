package by.varyvoda.bankapp.ui.util

import javafx.beans.property.Property

fun <T> Property<T>.observe(observer: (T) -> Unit) {
    observer(value)
    addListener { _, _, new -> observer(new) }
}