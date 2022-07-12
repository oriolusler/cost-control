package com.oriolsoler.costcontroler.infrastructure.controller.dto

data class UserDto(val username: String, val password: String) {
    constructor() : this("", "")
}