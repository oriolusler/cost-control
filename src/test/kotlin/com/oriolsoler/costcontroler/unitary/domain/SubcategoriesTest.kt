package com.oriolsoler.costcontroler.unitary.domain

import com.oriolsoler.costcontroler.domain.Subcategorises
import com.oriolsoler.costcontroler.domain.Subcategorises.NO_APPLY
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SubcategoriesTest {
    @Test
    fun `should return no_apply if subcategory does not exists`(){
        val result = Subcategorises.getOrEmpty("NO_EXISTS_SUBCATEGORY")
        assertEquals(NO_APPLY, result)
    }
}