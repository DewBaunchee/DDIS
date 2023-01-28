package by.varyvoda.bankapp.domain.model

import kotlin.properties.Delegates

class City {
    var id by Delegates.notNull<Int>()
    lateinit var name: String
}