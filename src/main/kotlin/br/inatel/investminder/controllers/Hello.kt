package br.inatel.investminder.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/hello")
@CrossOrigin(origins = ["http://localhost:3000"], maxAge=3600, allowCredentials = "true")
class Hello {
    @GetMapping
    fun hello(): String {
        return "Hello, World!"
    }
}