package com.oriolsoler.costcontroler.infrastructure.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class CostDto(
    @JsonProperty("date") @JsonFormat(pattern = "dd/MM/yyyy") val date: LocalDate,
    @JsonProperty("description") val description: String,
    @JsonProperty("category") val category: String,
    @JsonProperty("subcategory") val subcategory: String,
    @JsonProperty("comment") val comment: String,
    @JsonProperty("amount") val amount: Double
)
