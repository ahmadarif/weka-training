package com.ahmadarif.wekaclassifier

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class WekaClassifierApplication {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(WekaClassifierApplication::class.java, *args)
        }
    }
}