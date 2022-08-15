package com.oriolsoler.costcontroler.application.registerCost

import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
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
    val isPendingToPay: Boolean,
    val pendingToPayAmount: BigDecimal?
)

fun RegisterCostCommand.toCost() = Cost(
    date,
    Description(description),
    category,
    subcategory,
    comment,
    amount,
    username,
    isPendingToPay,
    pendingToPayAmount
)