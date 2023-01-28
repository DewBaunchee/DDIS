package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.dependency.DependencyProvider
import by.varyvoda.bankapp.domain.model.Client
import by.varyvoda.bankapp.domain.model.Sex
import by.varyvoda.bankapp.domain.repository.AbstractRepository
import by.varyvoda.bankapp.domain.repository.mapping.EntityMapping
import java.util.*

class ClientRepository : AbstractRepository<String, Client>(
    EntityMapping(
        "client",
        { Client() },
        DependencyProvider.get().run {
            val cityRep = provide(CityRepository::class.java)
            val maritalStatusRep = provide(MaritalStatusRepository::class.java)
            val countryRep = provide(CountryRepository::class.java)
            val disabilityRep = provide(DisabilityRepository::class.java)
            return@run listOf(
                EntityMapping.Field(
                    false, "last_name", String::class.java,
                    { lastName }, { lastName = it!! }
                ),
                EntityMapping.Field(
                    false, "first_name", String::class.java,
                    { firstName }, { firstName = it!! }
                ),
                EntityMapping.Field(
                    false, "patronymic", String::class.java,
                    { patronymic }, { patronymic = it!! }
                ),
                EntityMapping.Field(
                    false, "birthday", Date::class.java,
                    { birthday }, { birthday = it!! }
                ),
                EntityMapping.Field(
                    false, "sex", String::class.java,
                    { sex.toString().lowercase() }, { sex = Sex.valueOf(it!!.uppercase()) }
                ),
                EntityMapping.Field(
                    false, "passport_series", String::class.java,
                    { passportSeries }, { passportSeries = it!! }
                ),
                EntityMapping.Field(
                    false, "passport_number", String::class.java,
                    { passportNumber }, { passportNumber = it!! }
                ),
                EntityMapping.Field(
                    false, "produced_by", String::class.java,
                    { producedBy }, { producedBy = it!! }
                ),
                EntityMapping.Field(
                    false, "producing_date", Date::class.java,
                    { producingDate }, { producingDate = it!! }
                ),
                EntityMapping.Field(
                    true, "id", String::class.java,
                    { id }, { id = it!! }
                ),
                EntityMapping.Field(
                    false, "birthplace", String::class.java,
                    { birthplace }, { birthplace = it!! }
                ),
                EntityMapping.Field(
                    false, "residence_city", Int::class.java,
                    { residenceCity.id }, { residenceCity = cityRep.get(it!!)!! }
                ),
                EntityMapping.Field(
                    false, "residence_address", String::class.java,
                    { residenceAddress }, { residenceAddress = it!! }
                ),
                EntityMapping.Field(
                    false, "home_phone_number", String::class.java,
                    { homePhoneNumber }, { homePhoneNumber = it }
                ),
                EntityMapping.Field(
                    false, "mobile_phone_number", String::class.java,
                    { mobilePhoneNumber }, { mobilePhoneNumber = it }
                ),
                EntityMapping.Field(
                    false, "email", String::class.java,
                    { email }, { email = it }
                ),
                EntityMapping.Field(
                    false, "marital_status", Int::class.java,
                    { maritalStatus.id }, { maritalStatus = maritalStatusRep.get(it!!)!! }
                ),
                EntityMapping.Field(
                    false, "citizenship", String::class.java,
                    { citizenship.key }, { citizenship = countryRep.get(it!!)!! }
                ),
                EntityMapping.Field(
                    false, "disability", Int::class.java,
                    { disability.id }, { disability = disabilityRep.get(it!!)!! }
                ),
                EntityMapping.Field(
                    false, "pensioner", Boolean::class.java,
                    { pensioner }, { pensioner = it!! }
                ),
                EntityMapping.Field(
                    false, "month_income", Long::class.java,
                    { monthIncome }, { monthIncome = it }
                ),
                EntityMapping.Field(
                    false, "conscript", Boolean::class.java,
                    { conscript }, { conscript = it!! }
                ),
            )
        }
    )
)