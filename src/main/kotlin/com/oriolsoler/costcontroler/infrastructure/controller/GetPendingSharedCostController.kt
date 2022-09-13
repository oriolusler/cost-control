package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.infrastructure.repository.view.PostgresPendingSharedCostViewRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class GetPendingSharedCostController(private val postgresPendingSharedCostViewRepository: PostgresPendingSharedCostViewRepository) {
    @GetMapping("/get-pending-to-pay")
    fun getPendingSharedCost(model: Model, principal: Principal): String {
        model.addAttribute("costs", postgresPendingSharedCostViewRepository.findBy(principal.name))
        return "cost/pending-shared-cost"
    }
}