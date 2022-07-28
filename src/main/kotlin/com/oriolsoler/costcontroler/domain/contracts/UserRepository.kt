package com.oriolsoler.costcontroler.domain.contracts

import com.oriolsoler.costcontroler.domain.User

interface UserRepository {
    fun register(user: User)
    fun exists(username: String): Boolean
}