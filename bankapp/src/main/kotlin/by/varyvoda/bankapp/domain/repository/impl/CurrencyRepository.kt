package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.model.Currency
import by.varyvoda.bankapp.domain.repository.AbstractRepository
import by.varyvoda.bankapp.domain.repository.mapping.EntityMapping

class CurrencyRepository : AbstractRepository<String, Currency>(
    EntityMapping(
        "currency",
        { Currency() },
        listOf(
            EntityMapping.Field(
                "key", String::class.java,
                isId = true, isAutogenerated = false,
                getter = { key }, setter = { key = it!! }
            )
        )
    )
)