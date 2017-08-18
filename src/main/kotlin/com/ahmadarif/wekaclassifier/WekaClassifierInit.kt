package com.ahmadarif.wekaclassifier

import com.ahmadarif.wekaclassifier.storage.StorageService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
 * Created by Arif on 18-Aug-17.
 */
@Component
class WekaClassifierInit(val storageService: StorageService) : CommandLineRunner {
    override fun run(vararg p0: String?) {
        storageService.init()
    }
}