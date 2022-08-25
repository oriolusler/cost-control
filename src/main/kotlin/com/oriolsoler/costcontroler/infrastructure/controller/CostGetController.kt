package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.getcost.GetCostCommand
import com.oriolsoler.costcontroler.application.getcost.GetCost
import com.oriolsoler.costcontroler.domain.Categories
import com.oriolsoler.costcontroler.infrastructure.controller.dto.IdentifierDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.security.Principal

@Controller
class CostGetController(private val getCost: GetCost) {
    @GetMapping("/get/{id}")
    fun registerForm(@PathVariable id: IdentifierDto, model: Model, principal: Principal): String {
        val cost = getCost.execute(GetCostCommand(id.value))
        model.addAttribute("cost", cost.toDto())

        val map = Categories.getCategoriesWithSubtypes()
        model.addAttribute("categoriesMap", map)

        return "cost/get"
    }
}