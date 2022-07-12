package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.registerUser.RegisterUser
import com.oriolsoler.costcontroler.application.registerUser.UserRegistrationCommand
import com.oriolsoler.costcontroler.infrastructure.controller.dto.UserDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.context.request.WebRequest


@Controller
class RegisterController(private val registerUser: RegisterUser) {
    @GetMapping("/user/registration")
    fun showRegistrationForm(request: WebRequest?, model: Model): String? {
        val userDto = UserDto()
        model.addAttribute("user", userDto)
        return "registration"
    }

    @PostMapping("/user/registration")
    fun registerUserAccount(@ModelAttribute user: UserDto, model: Model): String {
        registerUser.execute(UserRegistrationCommand(user.username, user.password))
        return "redirect:/show"
    }
}