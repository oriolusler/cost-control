package com.oriolsoler.costcontroler.infrastructure.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HelloWorldController {
    @GetMapping
    fun helloWord() = "Hello world! This is osoler"
}