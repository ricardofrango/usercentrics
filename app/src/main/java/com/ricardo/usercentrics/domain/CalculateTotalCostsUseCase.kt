package com.ricardo.usercentrics.domain

import com.ricardo.usercentrics.domain.model.Service
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculateTotalCostsUseCase @Inject constructor() {

    operator fun invoke(services: List<Service>) =
        // calculate the total cost of the services
        services.sumOf { it.cost }
}