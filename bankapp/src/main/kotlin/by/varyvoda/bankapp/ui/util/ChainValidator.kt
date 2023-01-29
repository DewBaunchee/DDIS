package by.varyvoda.bankapp.ui.util

import tornadofx.*

typealias Validator<V> = (V) -> ValidationMessage?

class ChainValidator<V>(first: Validator<V>) {

    private val list = mutableListOf(first)

    fun then(validator: Validator<V>): ChainValidator<V> {
        list.add(validator)
        return this
    }

    fun validate(value: V): ValidationMessage? {
        for (i in 0 until list.lastIndex) {
            val result = list[i](value)
            if (result != null && result.severity != ValidationSeverity.Success) return result
        }
        return list.last()(value)
    }
}