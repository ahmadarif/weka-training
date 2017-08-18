package com.ahmadarif.wekaclassifier.service

import com.ahmadarif.wekaclassifier.extension.loader
import org.springframework.stereotype.Service
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.classifiers.bayes.NaiveBayesUpdateable
import weka.classifiers.functions.SMO
import weka.classifiers.trees.J48
import weka.core.Instance
import weka.core.Instances
import weka.core.SerializationHelper
import weka.core.Utils
import weka.core.converters.ArffLoader
import java.io.File
import java.util.*


/**
 * Created by ARIF on 20-Jul-17.
 */
@Service
class WekaService {

    fun sample1(file: File, className: String): String {
        // load data
        val loader = ArffLoader()
        loader.setFile(file)
        val data = loader.dataSet
        val classAttr = data.attribute(className)

        // set class
        data.setClassIndex(classAttr.index())

        val options = arrayOf("-U")
        val model = J48()
        model.options = options
        model.buildClassifier(data)

        return model.toString()
    }

    fun sample2(file: File, className: String): String {
        // load data
        val loader = loader(file)
        val data = loader.dataSet

        // set class
        data.setClassIndex(data.numAttributes() - 1)

        val options = arrayOf("-U")
        val model = J48()
        model.options = options
        model.buildClassifier(data)

        return model.toString()
    }

    fun sample3(file: File, className: String): String {
        // load data
        val loader = loader(file)
        val data = loader.dataSet
        val classAttr = data.attribute(className)

        // set class
        data.setClassIndex(classAttr.index())

        // train
        val model = J48()
        model.options = arrayOf("-U")
        model.buildClassifier(data)

        // test
        val eval = Evaluation(data)
        eval.crossValidateModel(model, data, 10, Random(1))
        eval.evaluateModel(model, data)

        println(eval.toClassDetailsString())

        return eval.toSummaryString("\nResults\n======\n", false)
    }

    fun sample4(file: File, className: String): String {
        // load data
        val loader = loader(file)
        val data:Instances = loader.dataSet
        val classAttr = data.attribute(className)

        // set class
        data.setClassIndex(classAttr.index())

        // train NaiveBayes
        val model = NaiveBayesUpdateable()
        model.buildClassifier(data)

        var current: Instance?
        do {
            current = loader.getNextInstance(data) ?: break
            model.updateClassifier(current)
        } while (true)

        // output generated model
        println(model)
        return model.toString()
    }

    fun sample5(file: File, className: String): String {
        val message = StringBuffer()
        val startTime = System.currentTimeMillis()

        // load data
        val loader = loader(file)
        val data = loader.dataSet
        val classAttr = data.attribute(className)

        // set class
        data.setClassIndex(classAttr.index())

        //SVM
        val model = SMO()

        val optionStr = "-C 1.0 -L 0.001 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01\""
        model.options = Utils.splitOptions(optionStr)
        model.buildClassifier(data)

        // prediction
        message.appendln()
        message.appendln("===== Prediction Result (Data Train) =====")

        val eval = Evaluation(data)
        eval.crossValidateModel(model, data, 5, Random(1))
        message.appendln(eval.toSummaryString())

        message.appendln("Time = ${milliToSecond(System.currentTimeMillis() - startTime)}")
        return message.toString()
    }

    fun sample6(modelFile: File, arff: File, className: String): String {
        val message = StringBuffer()
        val startTime = System.currentTimeMillis()

        // load data
        val loader = loader(arff)
        val data = loader.dataSet
        val classAttr = data.attribute(className)

        // set class
        data.setClassIndex(classAttr.index())

        // model
        val model = SerializationHelper.read(modelFile.absolutePath) as Classifier

        // prediction
        message.appendln()
        message.appendln("===== Prediction Result (Load Model) =====")

        val eval = Evaluation(data)
        eval.crossValidateModel(model, data, 5, Random(1))
        message.appendln(eval.toSummaryString())

        message.appendln("Time = ${milliToSecond(System.currentTimeMillis() - startTime)}")
        return message.toString()
    }

    private fun milliToSecond(mili: Long): Long {
        return (mili / 1000)
    }
}