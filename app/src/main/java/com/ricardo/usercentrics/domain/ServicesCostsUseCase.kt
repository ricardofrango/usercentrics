package com.ricardo.usercentrics.domain

import com.ricardo.usercentrics.di.Costs
import com.ricardo.usercentrics.di.RuleOneBankingSnoopy
import com.ricardo.usercentrics.di.RuleThreeTheGoodCitizenLimit
import com.ricardo.usercentrics.di.RuleTwoWhyDoYouCare
import com.ricardo.usercentrics.domain.model.Service
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsConsentUserResponse
import com.usercentrics.sdk.v2.settings.data.UsercentricsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@JvmSuppressWildcards
class ServicesCostsUseCase @Inject constructor(
    @Costs val costsMap: Map<String, Double>,
    @RuleOneBankingSnoopy val ruleOneBankingSnoopy: List<String>,
    @RuleTwoWhyDoYouCare val ruleTwoWhyDoYouCare: List<String>,
    @RuleThreeTheGoodCitizenLimit val ruleThreeTheGoodCitizenLimit: Int,
) {
    private val usercentricsSDK
        get() = Usercentrics.instance

    operator fun invoke(userResponse: UsercentricsConsentUserResponse): List<Service> {
        val cmpData = usercentricsSDK.getCMPData()

        // get the list of templateId of the consents with status = true
        val consents = userResponse.consents
            .filter { it.status }
            .map { it.templateId }

        // get the consented services by the templatedId
        val servicesConsented = cmpData.services
            .filter { consents.contains(it.templateId) }

        // get the services with their cost
        return servicesConsented.map {
            Service(
                name = it.dataProcessor ?: "",
                cost = calculateServiceCost(it)
            )
        }
    }

    private fun calculateServiceCost(usercentricsService: UsercentricsService): Int {
        val cost = usercentricsService.dataCollectedList.sumOf { costsMap[it] ?: 0.0 }
        var result = cost

        if (usercentricsService.dataCollectedList.containsAll(ruleOneBankingSnoopy)) {
            result += cost * 0.1
        }

        if (usercentricsService.dataCollectedList.containsAll(ruleTwoWhyDoYouCare)) {
            result += cost * 0.27
        }

        if (usercentricsService.dataCollectedList.size <= ruleThreeTheGoodCitizenLimit) {
            result -= cost * 0.1
        }

        return result.toInt()
    }
}