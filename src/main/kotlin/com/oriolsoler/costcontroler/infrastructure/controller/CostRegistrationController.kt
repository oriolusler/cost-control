package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.registerCost.RegisterCost
import com.oriolsoler.costcontroler.infrastructure.controller.dto.CostDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toCommand
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cost/register")
class CostRegistrationController(private val regitserCostUseCase: RegisterCost) {
    @PostMapping
    fun registerNewCost(@RequestBody cost: CostDto): ResponseEntity<Void> {
        regitserCostUseCase.execute(cost.toCommand())
        return ResponseEntity(CREATED)
    }
}