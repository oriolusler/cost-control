package com.oriolsoler.costcontroler.infrastructure.controller.dto

import com.oriolsoler.costcontroler.NoArgAnnotation
import com.oriolsoler.costcontroler.application.registerCost.RegisterCostCommand
import com.oriolsoler.costcontroler.application.registerCost.SharedCostCommand
import com.oriolsoler.costcontroler.application.updateCost.UpdateCostCommand
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
    var amount: String? = "",
    var shared: List<SharedCostDto> = ArrayList(20),
    var id: String? = ""
)

@NoArgAnnotation
data class SharedCostDto(
    var amount: BigDecimal? = ZERO,
    var debtor: String? = "",
    var paid: Boolean? = false
)

fun CostDto.toRegisterCommandWith(username: String): RegisterCostCommand {
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
        BigDecimal(amount),
        username,
        toList
    )
}

fun CostDto.toUpdateCommandWith(username: String): UpdateCostCommand {
    val toList = shared.map {
        SharedCostCommand(
            it.amount!!,
            it.debtor!!,
            it.paid!!
        )
    }.toList()
    return UpdateCostCommand(
        date!!,
        description!!,
        category!!,
        subcategory!!,
        comment!!,
        BigDecimal(amount),
        username,
        toList,
        id!!
    )
}

fun Cost.toDto() = CostDto(
    date!!,
    description!!.value,
    category?.displayName,
    subcategory?.displayName,
    comment!!,
    amount!!.toEngineeringString(),
    shared!!.map { SharedCostDto(it.amount, it.debtor, it.isPaid) },
    identifier.value.toString()
)