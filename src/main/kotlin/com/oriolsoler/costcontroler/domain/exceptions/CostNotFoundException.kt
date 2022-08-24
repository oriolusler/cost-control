package com.oriolsoler.costcontroler.domain.exceptions

import com.oriolsoler.costcontroler.domain.Id

class CostNotFoundException(val id: Id) : Exception("Cost with id: ${id.value} not found")