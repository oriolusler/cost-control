package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.domain.Categories.INCOME
import com.oriolsoler.costcontroler.domain.Categories.INVESTMENTS
import com.oriolsoler.costcontroler.infrastructure.repository.view.PostgresGetCostsRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
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

        val invested = costs.filter { it.category == INVESTMENTS.name }.sumOf { cost -> cost.amount }
        model.addAttribute("invested", invested)

        val income = costs.filter { it.category == INCOME.name }.sumOf { cost -> cost.amount }.abs()
        model.addAttribute("income", income)

        val expenses = costs
            .filter { it.category != INVESTMENTS.name && it.category != INCOME.name }
            .sumOf { cost -> cost.amount }
        model.addAttribute("expenses", expenses)

        val balancePerCategory = costs
            .filter { it.category != INVESTMENTS.name && it.category != INCOME.name }
            .groupBy { it.category }
            .mapValues { it.value.sumOf { cost -> cost.amount } }
        model.addAttribute("categoryBalancePerMonth", balancePerCategory)

        return "cost/greeting"
    }
}