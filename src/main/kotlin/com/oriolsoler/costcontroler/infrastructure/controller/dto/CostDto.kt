package com.oriolsoler.costcontroler.infrastructure.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.oriolsoler.costcontroler.application.registerCost.RegisterCostCommand
import com.oriolsoler.costcontroler.domain.Cost
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO.DATE
import java.time.LocalDate

data class CostDto(
    @JsonProperty("date") @DateTimeFormat(iso = DATE) val date: LocalDate,
    @JsonProperty("description") val description: String,
    @JsonProperty("category") val category: String,
    @JsonProperty("subcategory") val subcategory: String,
    @JsonProperty("comment") val comment: String,
    @JsonProperty("amount") val amount: Double
) {

    constructor() : this(
        LocalDate.now(),
        "",
        "",
        "",
        "",
        0.0
    )
}

fun CostDto.toCommandWith(username: String) =
    RegisterCostCommand(date, description, category, subcategory, comment, amount, username)

fun Cost.toDto() = CostDto(date, description.value, category, subcategory, comment, amount)