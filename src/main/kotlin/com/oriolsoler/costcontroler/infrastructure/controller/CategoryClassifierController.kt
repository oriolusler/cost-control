package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.infrastructure.services.NaiveBayesModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryClassifierController {
    private val nbyCategory = NaiveBayesModel()
    private val nbySubategory = NaiveBayesModel()


    @GetMapping("/expense/category/classifier")
    fun classifyCategory(@RequestParam expense: String): ClassifierResponse {
        nbyCategory.trainModel("src/main/resources/data/categories_data.txt")
        nbySubategory.trainModel("src/main/resources/data/subcategories_data.txt")

        return ClassifierResponse(nbyCategory.categorizeExpense(expense), nbySubategory.categorizeExpense(expense))
    }
}

data class ClassifierResponse(val category: String, val subcategory: String)