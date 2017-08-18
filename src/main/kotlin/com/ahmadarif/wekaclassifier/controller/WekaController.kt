package com.ahmadarif.wekaclassifier.controller

import com.ahmadarif.wekaclassifier.service.WekaService
import com.ahmadarif.wekaclassifier.storage.StorageService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.IOException

/**
 * Created by ARIF on 20-Jul-17.
 */
@Controller
class WekaController(val wekaService: WekaService, val storageService: StorageService) {

    @GetMapping("/")
    fun welcome(model: MutableMap<String, Any>): String {
        return "index"
    }

    @RequestMapping(value = "/upload", method = arrayOf(RequestMethod.POST))
    @Throws(IOException::class)
    fun uploadingPost(
            @RequestParam(required = false) fileModel: MultipartFile,
            @RequestParam(required = true) fileArff: MultipartFile,
            @RequestParam className: String,
            redirAttrs: RedirectAttributes
    ): String {
        reset()
        if (fileArff.isEmpty) append("- File Arff tidak boleh kosong.")
        if (className.isNullOrEmpty()) append("- Nama class harus diisi.")
        if (errors.length != EMPTY) {
            redirAttrs.addFlashAttribute("message", errors)
            return "redirect:/"
        }

        val arff = storageService.store(fileArff)
        if (fileModel.isEmpty) {
            val message = wekaService.sample5(arff, className)
            redirAttrs.addFlashAttribute("message", message)
        } else {
            val model = storageService.store(fileModel)
            redirAttrs.addFlashAttribute("message", "Modelnya kosong.")
        }

        return "redirect:/"
    }

    private val errors = StringBuilder()
    private val EMPTY = 14
    private fun append(str: String) {
        errors.append(str)
        errors.appendln()
    }
    private fun reset() {
        errors.setLength(0)
        errors.append(":: Errors ::")
        errors.appendln()
    }
}