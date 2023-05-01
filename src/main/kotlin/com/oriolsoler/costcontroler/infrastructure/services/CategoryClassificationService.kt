package com.oriolsoler.costcontroler.infrastructure.services

import opennlp.tools.doccat.DoccatFactory
import opennlp.tools.doccat.DoccatModel
import opennlp.tools.doccat.DocumentCategorizerME
import opennlp.tools.doccat.DocumentSample
import opennlp.tools.doccat.DocumentSampleStream
import opennlp.tools.util.MarkableFileInputStreamFactory
import opennlp.tools.util.ObjectStream
import opennlp.tools.util.PlainTextByLineStream
import opennlp.tools.util.TrainingParameters
import java.io.File
import java.io.IOException


class NaiveBayesModel {
    private var model: DoccatModel? = null
    @Throws(IOException::class, IOException::class)
    fun trainModel(trainingFile: File) {
        val dataInputStream = MarkableFileInputStreamFactory(trainingFile)
        // Create a stream of labeled documents from the training data
        val sampleStream: ObjectStream<DocumentSample> =
            DocumentSampleStream(PlainTextByLineStream(dataInputStream, "UTF-8"))
        val params = TrainingParameters()
        params.put(TrainingParameters.CUTOFF_PARAM, "0")
        model = DocumentCategorizerME.train("en", sampleStream, params, DoccatFactory())
    }

    fun categorizeExpense(expense: String): String {
        val categorizer = DocumentCategorizerME(model)
        val probabilities = categorizer.categorize(expense.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
        return categorizer.getBestCategory(probabilities)
    }
}