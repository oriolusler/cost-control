package com.oriolsoler.costcontroler.infrastructure.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class ImportCostsController {
    @GetMapping("/import")
    fun getImportPage(model: Model): String {
        return "cost/import"
    }
}