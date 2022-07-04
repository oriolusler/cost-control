package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.domain.Cost
import com.oriolsoler.costcontroler.domain.Description
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.LocalDate

@Controller
class CostShowController {
    @GetMapping("/show")
    fun registerForm(model: Model): String {
        model.addAttribute("costs", listOf(
            Cost(LocalDate.now(), Description("desc1"), "cat1", "sub1", "comm1", 10.1),
            Cost(LocalDate.now(), Description("desc2"), "cat2", "sub2", "comm2", 10.2)
        ))
        return "cost/show"
    }
}