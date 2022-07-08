package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.showCosts.ShowCosts
import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDate

@Controller
class CostShowController(private val showCostsUseCase: ShowCosts) {
    @GetMapping("/show")
    fun registerForm(model: Model): String {
        val costs = showCostsUseCase.execute()
        model.addAttribute("costs", costs)
        return "cost/show"
    }
}