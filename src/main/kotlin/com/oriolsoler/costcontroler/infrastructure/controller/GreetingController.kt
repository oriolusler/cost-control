package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.infrastructure.repository.view.PostgresGetCostsRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import java.math.BigDecimal
import java.security.Principal

@Controller
class GreetingController(private val getCostsRepository: PostgresGetCostsRepository) {
    @RequestMapping("/greeting")
    fun helloWord(
        @AuthenticationPrincipal(expression = "username") username: String,
        model: Model,
        principal: Principal
    ): String {
        model.addAttribute("username", username)
        val costs = getCostsRepository.currentMonthFor(username)

        val currentMonthValue = costs
            .groupBy { it.amount > BigDecimal.ZERO }
            .mapValues { it.value.sumOf { cost -> cost.amount }.abs() }

        val categoryBalancePerMonth = costs
            .filter { it.category != Categories.INVESTMENTS.name && it.category != Categories.INCOME.name }
            .groupBy { it.category }
            .mapValues { it.value.sumOf { cost -> cost.amount } }

        val originBalancePerMonth = costs
            .filter { it.category != Categories.INVESTMENTS.name && it.category != Categories.INCOME.name }
            .groupBy { it.origin }
            .mapValues { it.value.sumOf { cost -> cost.amount } }

        model.addAttribute("currentMonthValue", currentMonthValue)
        model.addAttribute("categoryBalancePerMonth", categoryBalancePerMonth)
        model.addAttribute("originBalancePerMonth", originBalancePerMonth)


        return "cost/greeting"
    }
}