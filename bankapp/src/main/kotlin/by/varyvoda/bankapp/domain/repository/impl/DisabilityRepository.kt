package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.model.Disability
import by.varyvoda.bankapp.domain.repository.AbstractRepository
import by.varyvoda.bankapp.domain.repository.mapping.EntityMapping

class DisabilityRepository : AbstractRepository<Int, Disability>(
    EntityMapping(
        "disability",
        { Disability() },
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