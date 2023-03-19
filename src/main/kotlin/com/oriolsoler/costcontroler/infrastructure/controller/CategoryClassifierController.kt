package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.infrastructure.services.NaiveBayesModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
class CategoryClassifierController {
    private val nbyCategory = NaiveBayesModel()
    private val nbySubategory = NaiveBayesModel()

    @GetMapping("/expense/category/classifier")
    fun classifyCategory(@RequestParam expense: String): ClassifierResponse {
        nbyCategory.trainModel(File(this::class.java.classLoader.getResource("data/categories_data.txt").toURI()))
        nbySubategory.trainModel(File(this::class.java.classLoader.getResource("data/subcategories_data.txt").toURI()))

        return ClassifierResponse(nbyCategory.categorizeExpense(expense), nbySubategory.categorizeExpense(expense))
    }
}

data class ClassifierResponse(val category: String, val subcategory: String)