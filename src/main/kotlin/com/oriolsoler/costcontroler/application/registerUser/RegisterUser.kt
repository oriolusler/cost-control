package com.oriolsoler.costcontroler.application.registerUser

import com.oriolsoler.costcontroler.domain.User
import com.oriolsoler.costcontroler.domain.contracts.UserRepository
import com.oriolsoler.costcontroler.domain.exceptions.UsernameAlreadyUsedException
import org.springframework.security.crypto.password.PasswordEncoder

class RegisterUser(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {
    fun execute(registerUserCommand: UserRegistrationCommand) {
        val username = registerUserCommand.username.lowercase()
        val password = registerUserCommand.password

        if (userRepository.exists(username)) {
            throw UsernameAlreadyUsedException(username)
        }

        val user = User(username, encode(password))
        userRepository.register(user)
    }

    private fun encode(password: String) = passwordEncoder.encode(password)
}