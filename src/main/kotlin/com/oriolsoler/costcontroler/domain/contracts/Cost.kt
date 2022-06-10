package com.oriolsoler.costcontroler.domain.contracts

import java.time.LocalDate

data class Cost(
    val date: LocalDate,
    val description: String,
    val category: String,
    val subcategory: String,
    val comment: String,
    val amount: Double
)
