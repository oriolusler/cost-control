package com.oriolsoler.costcontroler.application.updateCost

import com.oriolsoler.costcontroler.application.registerCost.SharedCostCommand
import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.CostIdentifier
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.SharedCost
import com.oriolsoler.costcontroler.domain.Subcategorises
import java.math.BigDecimal
import java.time.LocalDate

data class UpdateCostCommand(
    val dateNow: LocalDate,
    val description: String,
    val category: String,
    val subcategory: String,
    val comment: String,
    val amount: BigDecimal,
    val username: String,
    val sharedCost: List<SharedCostCommand>,
    val identifier: String
) {
    fun toCost() = Cost(
        dateNow,
        Description(description),
        Categories.valueOf(category),
        Subcategorises.valueOf(subcategory),
        comment,
        amount,
        username,
        sharedCost.map { SharedCost(it.amount, it.isPaid, it.debtor) }.toList(),
        CostIdentifier(identifier)
    )
}
