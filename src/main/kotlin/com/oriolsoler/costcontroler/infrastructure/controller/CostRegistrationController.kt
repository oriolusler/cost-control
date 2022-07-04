package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.registerCost.RegisterCost
import com.oriolsoler.costcontroler.infrastructure.controller.dto.CostDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toCommand
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class CostRegistrationController(private val regitserCostUseCase: RegisterCost) {

    @GetMapping("/register")
    fun registerForm(model: Model): String {
        model.addAttribute("cost", CostDto())
        return "cost/register"
    }

    @PostMapping("/register")
    fun registerNewCost(@ModelAttribute cost: CostDto, model: Model): String {
        regitserCostUseCase.execute(cost.toCommand())
        return "redirect:/show"
    }
}