package com.oriolsoler.costcontroler.infrastructure.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {
    @GetMapping("/greeting")
    fun helloWord(@AuthenticationPrincipal(expression = "username") username: String) = "Hello, $username"
}