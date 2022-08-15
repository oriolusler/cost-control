package com.oriolsoler.costcontroler.domain.exceptions

class InvalidPendingAmountException : Exception("Amount pending is not set or is lower than or equals 0")