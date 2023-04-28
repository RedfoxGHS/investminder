package br.inatel.investminder.services

import br.inatel.investminder.controllers.dtos.request.CreateUserRequestDTO
import br.inatel.investminder.entities.User
import br.inatel.investminder.exceptions.AccountAlreadyExistException
import br.inatel.investminder.exceptions.CreateAccountException
import br.inatel.investminder.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.Base64

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(createUserRequestDTO: CreateUserRequestDTO): User {

        if (!validateEmail(createUserRequestDTO.email)) {
            throw CreateAccountException("Invalid email")
        }

        if (createUserRequestDTO.password != createUserRequestDTO.passwordConfirmation) {
            throw CreateAccountException("Password and password confirmation must be the same")
        }

        if (!validatePassword(createUserRequestDTO.password)) {
            throw CreateAccountException("Password must have at least 6 characters")
        }

        createUserRequestDTO.cpf.replace(".", "").replace("-", "")
        if (!validateCpf(createUserRequestDTO.cpf)) {
            throw CreateAccountException("Invalid CPF")
        }

        if (userRepository.findByEmail(createUserRequestDTO.email) != null) {
            throw AccountAlreadyExistException("Email already exists")
        }

        val user = User(
                firstName = createUserRequestDTO.firstName,
                lastName = createUserRequestDTO.lastName,
                email = createUserRequestDTO.email,
                password = encodePassword(createUserRequestDTO.password),
                cpf = encodeCpf(createUserRequestDTO.cpf)
        )
        return userRepository.save(user)
    }

    private fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun validateCpf(cpf: String): Boolean {
        return cpf.length == 11
    }

    private fun encodePassword(password: String): String {
        return Base64.getEncoder().encodeToString(password.toByteArray())
    }

    private fun encodeCpf(cpf: String): String {
        return Base64.getEncoder().encodeToString(cpf.toByteArray())
    }
}