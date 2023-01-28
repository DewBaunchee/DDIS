package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.model.MaritalStatus
import by.varyvoda.bankapp.domain.repository.AbstractRepository
import by.varyvoda.bankapp.domain.repository.mapping.EntityMapping

class MaritalStatusRepository : AbstractRepository<Int, MaritalStatus>(
    EntityMapping(
        "marital_status",
        { MaritalStatus() },
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