package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.model.Country
import by.varyvoda.bankapp.domain.repository.AbstractRepository
import by.varyvoda.bankapp.domain.repository.mapping.EntityMapping

class CountryRepository : AbstractRepository<String, Country>(
    EntityMapping(
        "country",
        { Country() },
        listOf(
            EntityMapping.Field(
                true, "key", String::class.java,
                { key }, { key = it!! }
            ),
            EntityMapping.Field(
                true, "name", String::class.java,
                { name }, { name = it!! }
            ),
        )
    )
)