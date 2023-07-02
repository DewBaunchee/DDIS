package by.varyvoda.bankapp.domain.repository.impl

import by.varyvoda.bankapp.domain.model.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : JpaRepository<City, Int>