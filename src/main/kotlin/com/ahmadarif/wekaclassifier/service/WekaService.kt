package com.ahmadarif.wekaclassifier.service

import org.springframework.stereotype.Service
import weka.classifiers.trees.J48
import weka.core.Instances
import java.io.BufferedReader
import java.io.FileReader
import weka.core.converters.ArffLoader
import java.io.File


/**
 * Created by ARIF on 20-Jul-17.
 */
@Service
class WekaService {

    fun sample1(file: File): String {
        // load data
        val loader = ArffLoader()
        loader.setFile(file)
        val data = loader.structure

        // set class
        data.setClassIndex(data.numAttributes() - 1)

        val options = arrayOf("-U")
        val tree = J48()         // new instance of tree
        tree.options = options     // set the options
        tree.buildClassifier(data)   // build classifier

        return tree.toSummaryString()
    }

}