package com.oriolsoler.costcontroler.infrastructure.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.oriolsoler.costcontroler.application.registerCost.RegisterCostCommand
import com.oriolsoler.costcontroler.domain.Cost
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO.DATE
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.time.LocalDate

data class CostDto(
    @JsonProperty("date") @DateTimeFormat(iso = DATE) val date: LocalDate,
    @JsonProperty("description") val description: String,
    @JsonProperty("category") val category: String,
    @JsonProperty("subcategory") val subcategory: String,
    @JsonProperty("comment") val comment: String,
    @JsonProperty("amount") val amount: BigDecimal,
    @JsonProperty("isPendingToPay") val isPendingToPay: Boolean? = false,
    @JsonProperty("pendingToPayAmount") val pendingToPayAmount: BigDecimal?
) {

    constructor() : this(
        LocalDate.now(),
        "",
        "",
        "",
        "",
        ZERO,
        false,
        null
    )
}

fun CostDto.toCommandWith(username: String) =
    RegisterCostCommand(
        date,
        description,
        category,
        subcategory,
        comment,
        amount,
        username,
        isPendingToPay!!,
        pendingToPayAmount
    )

fun Cost.toDto() =
    CostDto(date, description.value, category, subcategory, comment, amount, isPendingToPay, pendingToPayAmount)