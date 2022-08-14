package com.oriolsoler.costcontroler.infrastructure.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class GreetingController {
    @RequestMapping("/greeting")
    fun helloWord(@AuthenticationPrincipal(expression = "username") username: String,  model:Model): String {
        model.addAttribute("username", username)
        return "cost/greeting"
    }
}