package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.model.MaritalStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MaritalStatusRepository : JpaRepository<MaritalStatus, Int>