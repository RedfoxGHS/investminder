package br.inatel.investminder.controllers

import br.inatel.investminder.controllers.dtos.request.CreateUserRequestDTO
import br.inatel.investminder.controllers.dtos.request.LoginResquestDTO
import br.inatel.investminder.entities.User
import br.inatel.investminder.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = ["http://localhost:3000"], maxAge=3600, allowCredentials = "true")
class UsersController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody createUserRequestDTO: CreateUserRequestDTO): ResponseEntity<User> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserRequestDTO))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginResquestDTO: LoginResquestDTO): ResponseEntity<User> {
        return ResponseEntity.ok(userService.login(loginResquestDTO))
    }

    @GetMapping("/{id}")
    fun getNameUserById(@RequestBody id: Long): ResponseEntity<String> {
        return ResponseEntity.ok(userService.getNameUserById(id))
    }


}