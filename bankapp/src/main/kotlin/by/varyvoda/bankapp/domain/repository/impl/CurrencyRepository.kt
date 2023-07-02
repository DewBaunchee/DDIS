package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.model.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRepository : JpaRepository<Currency, String>