package by.varyvoda.bankapp.domain.model

import java.util.*

class Client {
    lateinit var lastName: String
    lateinit var firstName: String
    lateinit var patronymic: String
    lateinit var birthday: Date
    lateinit var sex: Sex
    lateinit var passportSeries: String
    lateinit var passportNumber: String
    lateinit var producedBy: String
    lateinit var producingDate: Date
    lateinit var id: String
    lateinit var birthplace: String
    lateinit var residenceCity: City
    lateinit var residenceAddress: String
    var homePhoneNumber: String? = null
    var mobilePhoneNumber: String? = null
    var email: String? = null
    lateinit var maritalStatus: MaritalStatus
    lateinit var citizenship: Country
    lateinit var disability: Disability
    var pensioner: Boolean = false
    var monthIncome: Long? = null
    var conscript: Boolean = true
}