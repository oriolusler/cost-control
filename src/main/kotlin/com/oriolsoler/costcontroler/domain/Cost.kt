package com.oriolsoler.costcontroler.domain

import java.time.LocalDate

data class Cost(
    val date: LocalDate,
    val description: Description,
    val category: String,
    val subcategory: String,
    val comment: String,
    val amount: Double,
    val username: String
)

data class Description(val value: String)
