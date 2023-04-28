package br.inatel.investminder.controllers

import br.inatel.investminder.controllers.dtos.request.CreateUserRequestDTO
import br.inatel.investminder.entities.User
import br.inatel.investminder.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/users")
class UsersController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody createUserRequestDTO: CreateUserRequestDTO): ResponseEntity<User> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserRequestDTO))
    }
}