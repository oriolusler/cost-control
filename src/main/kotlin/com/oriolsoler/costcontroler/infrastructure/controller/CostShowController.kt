package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.showCosts.ShowCostCommand
import com.oriolsoler.costcontroler.application.showCosts.ShowCosts
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class CostShowController(private val showCostsUseCase: ShowCosts) {
    @GetMapping("/show")
    fun registerForm(model: Model, principal: Principal): String {
        val showCostCommand = ShowCostCommand(principal.name)
        val costs = showCostsUseCase.execute(showCostCommand)
        model.addAttribute("costs", costs)
        return "cost/show"
    }
}