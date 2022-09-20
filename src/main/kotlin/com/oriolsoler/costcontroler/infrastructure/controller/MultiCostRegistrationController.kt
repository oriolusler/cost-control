package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.infrastructure.controller.dto.CostDto
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class MultiCostRegistrationController {

    @PostMapping(value = ["register/multi"])
    fun registerMultipleNewCost(
        @RequestBody cost: Array<CostDto>,
        model: Model,
        principal: Principal
    ): String {
        return "redirect:/show"
    }
}