package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.registerCost.RegisterCost
import com.oriolsoler.costcontroler.domain.CostCategories
import com.oriolsoler.costcontroler.domain.CostSubCategorises
import com.oriolsoler.costcontroler.infrastructure.controller.dto.CostDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.SharedCostDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toCommandWith
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.security.Principal

@Controller
class CostRegistrationController(private val registerCostUseCase: RegisterCost) {

    @GetMapping("/register")
    fun registerForm(model: Model): String {
        val sharedCosts = mutableListOf<SharedCostDto>()
        model.addAttribute("cost", CostDto(null, null, null, null, null, null, sharedCosts))
        val map = CostCategories.values()
            .associateWith { it.subtypes.map { sub -> SubtypeDto(sub.name, sub.displayName) }.toList() }
        model.addAttribute("categoriesMap", map)
        return "cost/register"
    }

    @RequestMapping(value = ["register"], method = [RequestMethod.POST])
    fun registerNewCost(@ModelAttribute("cost") cost: CostDto, model: Model, principal: Principal): String {
        val registerCostCommand = cost.toCommandWith(principal.name)
        registerCostUseCase.execute(registerCostCommand)
        return "redirect:/show"
    }
}

data class SubtypeDto(val name: String, val displayName: String)