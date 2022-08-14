package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.registerCost.RegisterCost
import com.oriolsoler.costcontroler.domain.CostCategories
import com.oriolsoler.costcontroler.infrastructure.controller.dto.CostDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toCommandWith
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal

@Controller
class CostRegistrationController(private val registerCostUseCase: RegisterCost) {

    @GetMapping("/register")
    fun registerForm(model: Model): String {
        model.addAttribute("cost", CostDto())
        val map = CostCategories.values().associate { it.name to it.subtypes }
        model.addAttribute("categoriesMap", map)
        return "cost/register"
    }

    @PostMapping("/register")
    fun registerNewCost(@ModelAttribute cost: CostDto, model: Model, principal: Principal): String {
        registerCostUseCase.execute(cost.toCommandWith(principal.name))
        return "redirect:/show"
    }
}