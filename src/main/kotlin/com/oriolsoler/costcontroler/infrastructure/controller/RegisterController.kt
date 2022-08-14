package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.application.registerUser.RegisterUser
import com.oriolsoler.costcontroler.application.registerUser.UserRegistrationCommand
import com.oriolsoler.costcontroler.domain.exceptions.UsernameAlreadyUsedException
import com.oriolsoler.costcontroler.infrastructure.controller.dto.UserDto
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView


@Controller
class RegisterController(private val registerUser: RegisterUser) {
    @GetMapping("/user/registration")
    fun showRegistrationForm(request: WebRequest?, model: Model): String? {
        val userDto = UserDto()
        model.addAttribute("user", userDto)
        return "user/registration"
    }

    @PostMapping("/user/registration")
    fun registerUserAccount(@ModelAttribute user: UserDto, model: Model): String {
        registerUser.execute(UserRegistrationCommand(user.username, user.password))
        return "redirect:/show"
    }

    @ExceptionHandler(value = [UsernameAlreadyUsedException::class])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUsernameAlreadyUsedException(e: UsernameAlreadyUsedException): ModelAndView {
        val modelView = ModelAndView()
        val userDto = UserDto()
        modelView.viewName = "user/registration"
        modelView.addObject("user", userDto)
        modelView.addObject("error", e.message)
        return modelView
    }
}