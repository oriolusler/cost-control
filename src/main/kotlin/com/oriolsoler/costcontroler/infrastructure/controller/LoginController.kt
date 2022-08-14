package com.oriolsoler.costcontroler.infrastructure.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class LoginController {
    @RequestMapping("/login")
    fun login() = "user/login"
}