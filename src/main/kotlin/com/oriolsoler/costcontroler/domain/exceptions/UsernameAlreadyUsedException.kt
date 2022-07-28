package com.oriolsoler.costcontroler.domain.exceptions

class UsernameAlreadyUsedException(username: String) :
    Exception("Username $username already exists, try another one")