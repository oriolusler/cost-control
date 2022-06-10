package com.oriolsoler.costcontroler.domain.contracts

import java.time.LocalDate

data class Cost(
    val date: LocalDate,
    val description: Description,
    val category: String,
    val subcategory: String,
    val comment: String,
    val amount: Double
)

data class Description(val value: String)
