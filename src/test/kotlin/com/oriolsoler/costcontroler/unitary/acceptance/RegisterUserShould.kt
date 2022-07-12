package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.willReturn
import com.oriolsoler.costcontroler.application.registerUser.RegisterUser
import com.oriolsoler.costcontroler.application.registerUser.UserRegistrationCommand
import com.oriolsoler.costcontroler.domain.User
import com.oriolsoler.costcontroler.domain.contracts.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder

class RegisterUserShould {
    @Test
    fun `should register user use case`() {
        val userRepository = mock<UserRepository>()
        val passwordEncoder = mock<PasswordEncoder>()
        given { passwordEncoder.encode("Oriol12345") } willReturn { "Encrypted-password" }

        val registerUserCommand = UserRegistrationCommand("Oriol", "Oriol12345")
        val userCostUseCase = RegisterUser(userRepository, passwordEncoder)

        userCostUseCase.execute(registerUserCommand)

        verify(passwordEncoder).encode(registerUserCommand.password)
        verify(userRepository).register(User(registerUserCommand.username, "Encrypted-password"))
    }
}