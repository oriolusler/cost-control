package com.oriolsoler.costcontroler.domain.exceptions

import com.oriolsoler.costcontroler.domain.CostIdentifier

class CostNotFoundException(val costIdentifier: CostIdentifier) : Exception("Cost with id: ${costIdentifier.value} not found")