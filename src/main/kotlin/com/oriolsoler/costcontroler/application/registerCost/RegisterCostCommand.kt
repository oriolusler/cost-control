package com.oriolsoler.costcontroler.application.registerCost

import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import com.oriolsoler.costcontroler.domain.SharedCost
import com.oriolsoler.costcontroler.domain.Subcategorises
import java.math.BigDecimal
import java.time.LocalDate

data class RegisterCostCommand(
    val date: LocalDate,
    val description: String,
    val category: String,
    val subcategory: String,
    val comment: String,
    val amount: BigDecimal,
    val username: String,
    val shared: List<SharedCostCommand>
)

data class SharedCostCommand(
    val amount: BigDecimal,
    val debtor: String,
    val isPaid: Boolean
)

fun RegisterCostCommand.toCost(): Cost {
    val toList = shared.map { SharedCost(it.amount, it.isPaid, it.debtor) }.toList()

    return Cost(
        date,
        Description(description),
        Categories.valueOf(category),
        Subcategorises.valueOf(subcategory),
        comment,
        amount,
        username,
        shared.map { SharedCost(it.amount, it.isPaid, it.debtor) }.toList()

    )
}
