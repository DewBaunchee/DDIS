package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.model.City
import by.varyvoda.bankapp.domain.repository.AbstractRepository
import by.varyvoda.bankapp.domain.repository.mapping.EntityMapping

class CityRepository : AbstractRepository<Int, City>(
    EntityMapping(
        "city",
        { City() },
        listOf(
            EntityMapping.Field(
                true, "id", Int::class.java,
                { id }, { id = it!! }
            ),
            EntityMapping.Field(
                true, "name", String::class.java,
                { name }, { name = it!! }
            ),
        )
    )
)