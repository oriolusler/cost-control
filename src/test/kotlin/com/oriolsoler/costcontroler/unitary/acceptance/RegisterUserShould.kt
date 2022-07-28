package com.oriolsoler.costcontroler.unitary.acceptance

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.willReturn
import com.oriolsoler.costcontroler.application.registerUser.RegisterUser
import com.oriolsoler.costcontroler.application.registerUser.UserRegistrationCommand
import com.oriolsoler.costcontroler.domain.User
import com.oriolsoler.costcontroler.domain.contracts.UserRepository
import com.oriolsoler.costcontroler.domain.exceptions.UsernameAlreadyUsedException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder

class RegisterUserShould {
    @Test
    fun `should register user use case`() {
        val userRepository = mock<UserRepository>()
        val passwordEncoder = mock<PasswordEncoder>()
        given { passwordEncoder.encode("Oriol12345") } willReturn { "Encrypted-password" }
        given { userRepository.exists("Oriol") } willReturn { false }
        val registerUserCommand = UserRegistrationCommand("Oriol", "Oriol12345")
        val userCostUseCase = RegisterUser(userRepository, passwordEncoder)

        userCostUseCase.execute(registerUserCommand)

        verify(passwordEncoder).encode(registerUserCommand.password)
        verify(userRepository).exists("oriol")
        verify(userRepository).register(User("oriol", "Encrypted-password"))
    }

    @Test
    fun `should not register user if usrename already used use case`() {
        val userRepository = mock<UserRepository>()
        val passwordEncoder = mock<PasswordEncoder>()
        given { passwordEncoder.encode("Oriol12345") } willReturn { "Encrypted-password" }
        given { userRepository.exists("oriol") } willReturn { true }

        val registerUserCommand = UserRegistrationCommand("Oriol", "Oriol12345")
        val userCostUseCase = RegisterUser(userRepository, passwordEncoder)

        assertThrows<UsernameAlreadyUsedException> { userCostUseCase.execute(registerUserCommand) }

        verify(userRepository).exists("oriol")
        verify(passwordEncoder, never()).encode(any())
        verify(userRepository, never()).register(any())
    }
}