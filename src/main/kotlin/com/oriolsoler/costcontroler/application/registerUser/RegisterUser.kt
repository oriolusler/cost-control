package com.oriolsoler.costcontroler.application.registerUser

import com.oriolsoler.costcontroler.domain.User
import com.oriolsoler.costcontroler.domain.contracts.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder

class RegisterUser(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {
    fun execute(registerUserCommand: UserRegistrationCommand) {
        val user = User(
            registerUserCommand.username,
            passwordEncoder.encode(registerUserCommand.password)
        )
        userRepository.register(user)
    }
}