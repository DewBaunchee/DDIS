package by.varyvoda.bankapp.domain.repository.init

import by.varyvoda.bankapp.domain.dependency.DependencyProvider
import by.varyvoda.bankapp.domain.model.*
import by.varyvoda.bankapp.domain.repository.impl.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Period

fun initializeRepositories() = runBlocking {
    val depProvider = DependencyProvider.get()

    launch {
        val cityRepository = depProvider.provide(CityRepository::class.java)
        val cityNames = listOf("Minsk", "Grodno", "Mogilev", "Brest")
        val cities = cityRepository.getAll()
        cityNames.forEach { name ->
            if (cities.none { it.name == name })
                cityRepository.save(City().apply { this.name = name })
        }
    }
    launch {
        val countryRepository = depProvider.provide(CountryRepository::class.java)
        val countryNames =
            listOf(
                "BLR" to "Belarus", "RUS" to "Russia",
                "UKR" to "Ukraine", "POL" to "Poland"
            )
        val countries = countryRepository.getAll()
        countryNames.forEach { countryName ->
            if (countries.none { it.key == countryName.first })
                countryRepository.save(Country().apply {
                    this.key = countryName.first
                    this.name = countryName.second
                })
        }
    }
    launch {
        val maritalStatusRepository = depProvider.provide(MaritalStatusRepository::class.java)
        val maritalStatusesNames = listOf("Single", "Married", "Civil Marriage", "Divorced", "Widow")
        val maritalStatuses = maritalStatusRepository.getAll()
        maritalStatusesNames.forEach { name ->
            if (maritalStatuses.none { it.name == name })
                maritalStatusRepository.save(MaritalStatus().apply { this.name = name })
        }
    }
    launch {
        val currencyRepository = depProvider.provide(CurrencyRepository::class.java)
        val currencyKeys = listOf("BYN", "USD", "EUR")
        val currencies = currencyRepository.getAll()
        currencyKeys.forEach { key ->
            if (currencies.none { it.key == key })
                currencyRepository.save(Currency().apply { this.key = key })
        }
    }
    launch {
        val depositProgramRepository = depProvider.provide(DepositProgramRepository::class.java)
        val programs = listOf(
            Deposit.Program().apply {
                name = "Successful online"
                revocable = false
                period = Period.ofMonths(13)
                firstPayment = 150_000
                percentStrategy = Deposit.Strategy(
                    listOf(
                        Deposit.Strategy.Entry(Period.ofMonths(6), 14.05),
                        Deposit.Strategy.Entry(Period.ofMonths(5), 13.55),
                    )
                )
                replenishment = ""
                expenditureOperations = ""
                autoProlongation = false
            },
            Deposit.Program().apply {
                name = "Fruitful"
                revocable = true
                period = Period.ofMonths(6)
                firstPayment = 150
                percentStrategy = Deposit.Strategy(
                    listOf(
                        Deposit.Strategy.Entry(Period.ofMonths(6), 2.5),
                    )
                )
                replenishment = ""
                expenditureOperations = ""
                autoProlongation = false
            },
        )
        val savedPrograms = depositProgramRepository.getAll()
        programs.forEach { program ->
            if (savedPrograms.none { it.name == program.name })
                depositProgramRepository.save(program)
        }
    }

    joinAll()
}