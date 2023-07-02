package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, String> {

    fun getByPassportSeriesAndPassportNumber(series: String, number: String): Client?
}