package com.oriolsoler.costcontroler.application.registerCost

import com.oriolsoler.costcontroler.domain.contracts.Cost
import com.oriolsoler.costcontroler.domain.contracts.Description
import java.time.LocalDate

data class RegisterCostCommand(
    val date: LocalDate,
    val description: String,
    val category: String,
    val subcategory: String,
    val comment: String,
    val amount: Double
)

fun RegisterCostCommand.toCost() = Cost(date, Description(description), category, subcategory, comment, amount)