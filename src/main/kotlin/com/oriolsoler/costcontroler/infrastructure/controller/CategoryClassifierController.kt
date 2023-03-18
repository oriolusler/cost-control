package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.infrastructure.services.NaiveBayesModel
import org.springframework.core.io.ClassPathResource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryClassifierController {
    private val nbyCategory = NaiveBayesModel()
    private val nbySubategory = NaiveBayesModel()

    @GetMapping("/expense/category/classifier")
    fun classifyCategory(@RequestParam expense: String): ClassifierResponse {
        nbyCategory.trainModel(ClassPathResource("data/categories_data.txt").file)
        nbySubategory.trainModel(ClassPathResource("data/subcategories_data.txt").file)

        return ClassifierResponse(nbyCategory.categorizeExpense(expense), nbySubategory.categorizeExpense(expense))
    }
}

data class ClassifierResponse(val category: String, val subcategory: String)