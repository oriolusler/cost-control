package com.oriolsoler.costcontroler.infrastructure.controller.dto

import com.oriolsoler.costcontroler.NoArgAnnotation
import com.oriolsoler.costcontroler.application.registerCost.RegisterCostCommand
import com.oriolsoler.costcontroler.application.registerCost.SharedCostCommand
import com.oriolsoler.costcontroler.domain.Cost
import org.springframework.format.annotation.DateTimeFormat
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.time.LocalDate

@NoArgAnnotation
data class CostDto(
    @field:DateTimeFormat(pattern = "yyyy-MM-dd") var date: LocalDate? = LocalDate.now(),
    var description: String? = "",
    var category: String? = "",
    var subcategory: String? = "",
    var comment: String? = "",
    var amount: BigDecimal? = ZERO,
    var shared: List<SharedCostDto> = ArrayList(20)
)

@NoArgAnnotation
data class SharedCostDto(
    var amount: BigDecimal? = ZERO,
    var debtor: String? = "",
    var paid: Boolean? = false
)

fun CostDto.toCommandWith(username: String): RegisterCostCommand {
    val toList = shared.map {
        SharedCostCommand(
            it.amount!!,
            it.debtor!!,
            it.paid!!
        )
    }.toList()
    return RegisterCostCommand(
        date!!,
        description!!,
        category!!,
        subcategory!!,
        comment!!,
        amount!!,
        username,
        toList
        // shared!!.map { ObjectMapper().readValue(it,SharedCostDto::class.java) }.toList()
    )
}


fun Cost.toDto() = CostDto(date!!, description!!.value, category?.displayName, subcategory?.displayName, comment!!, amount!!)