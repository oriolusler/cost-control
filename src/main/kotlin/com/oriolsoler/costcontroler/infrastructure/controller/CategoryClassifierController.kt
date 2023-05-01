package com.oriolsoler.costcontroler.infrastructure.controller

import com.oriolsoler.costcontroler.infrastructure.services.NaiveBayesModel
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@RestController
class CategoryClassifierController {
    private val nbyCategory = NaiveBayesModel()
    private val nbySubategory = NaiveBayesModel()

    @GetMapping("/expense/category/classifier")
    fun classifyCategory(@RequestParam expense: String): ClassifierResponse {
        nbyCategory.trainModel(stream2file(this::class.java.classLoader.getResourceAsStream("data/categories_data.txt")))
        nbySubategory.trainModel(stream2file(this::class.java.classLoader.getResourceAsStream("data/subcategories_data.txt")))

        return ClassifierResponse(nbyCategory.categorizeExpense(expense), nbySubategory.categorizeExpense(expense))
    }

    fun stream2file(input: InputStream?): File {
        val tempFile = File.createTempFile("temp", ".txt")
        tempFile.deleteOnExit()
        FileOutputStream(tempFile).use { out -> IOUtils.copy(input, out) }
        return tempFile
    }
}

data class ClassifierResponse(val category: String, val subcategory: String)