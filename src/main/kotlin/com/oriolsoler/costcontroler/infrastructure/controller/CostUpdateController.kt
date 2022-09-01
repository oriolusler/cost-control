package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.updateCost.UpdateCost
import com.oriolsoler.costcontroler.domain.exceptions.CostNotFoundException
import com.oriolsoler.costcontroler.infrastructure.controller.dto.CostDto
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toRegisterCommandWith
import com.oriolsoler.costcontroler.infrastructure.controller.dto.toUpdateCommandWith
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.security.Principal

@Controller
class CostUpdateController(private val updateCost: UpdateCost) {

    @RequestMapping(value = ["update"], method = [RequestMethod.POST])
    fun registerNewCost(@ModelAttribute("cost") cost: CostDto, model: Model, principal: Principal): String {
        updateCost.execute(cost.toUpdateCommandWith(principal.name))
        return "redirect:/show"
    }/*

    @ExceptionHandler(value = [CostNotFoundException::class])
    fun handleCostNotFoundException(e: CostNotFoundException) = ResponseEntity<String>(e.message, BAD_REQUEST)*/
}