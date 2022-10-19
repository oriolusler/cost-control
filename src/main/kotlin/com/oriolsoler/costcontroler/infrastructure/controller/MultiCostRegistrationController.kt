package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.registerCost.multi.MultiRegisterCost
import com.oriolsoler.costcontroler.application.registerCost.multi.MultiRegisterCostCommand
import com.oriolsoler.costcontroler.infrastructure.controller.dto.CostDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toRegisterCommandWith
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class MultiCostRegistrationController(private val multiRegisterCost: MultiRegisterCost) {

    @PostMapping(value = ["register/multi"])
    fun registerMultipleNewCost(
        @RequestBody cost: Array<CostDto>,
        model: Model,
        principal: Principal
    ): String {
        val commandList = cost.map { it.toRegisterCommandWith(principal.name) }
        multiRegisterCost.execute(MultiRegisterCostCommand(commandList))
        return "redirect:/show"
    }
}