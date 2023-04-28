package br.inatel.investminder.services

import br.inatel.investminder.controllers.dtos.request.CreateUserRequestDTO
import br.inatel.investminder.entities.User
import br.inatel.investminder.repositories.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class UserServiceTest {

    private lateinit var userService: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userService = UserService(userRepository)
    }

    @Test
    fun `should create user`() {
        val createUserRequestDTO = CreateUserRequestDTO(
                firstName = "Test",
                lastName = "Test",
                email = "test@test.com",
                password = "123456",
                passwordConfirmation = "123456",
                cpf = "12345678901"
        )

        val expetedUser = User(
                firstName = "Test",
                lastName = "Test",
                email = "test@test.com",
                password = Base64.getEncoder().encodeToString("123456".toByteArray()),
                cpf = Base64.getEncoder().encodeToString("12345678901".toByteArray())
        )


        `when`(userRepository.save(any())).thenReturn(expetedUser)

        val user = userService.createUser(createUserRequestDTO)

        assertEquals(expetedUser, user)
    }

    @Test
    fun `should not create user with invalid email`() {
        val createUserRequestDTO = CreateUserRequestDTO(
                firstName = "Test",
                lastName = "Test",
                email = "testtest.com",
                password = "123456",
                passwordConfirmation = "123456",
                cpf = "12345678901"
        )

        try {
            userService.createUser(createUserRequestDTO)
        } catch (e: Exception) {
            assertEquals("Invalid email", e.message)
        }
    }

    @Test
    fun `should not create user with different password and password confirmation`() {
        val createUserRequestDTO = CreateUserRequestDTO(
                firstName = "Test",
                lastName = "Test",
                email = "test@test.com",
                password = "123456",
                passwordConfirmation = "1234567",
                cpf = "12345678901"
        )

        try {
            userService.createUser(createUserRequestDTO)
        } catch (e: Exception) {
            assertEquals("Password and password confirmation must be the same", e.message)
        }
    }

    @Test
    fun `should not create user with password less than 6 characters`() {
        val createUserRequestDTO = CreateUserRequestDTO(
                firstName = "Test",
                lastName = "Test",
                email = "test@test.com",
                password = "12345",
                passwordConfirmation = "12345",
                cpf = "12345678901"
        )

        try {
            userService.createUser(createUserRequestDTO)
        } catch (e: Exception) {
            assertEquals("Password must have at least 6 characters", e.message)
        }
    }

    @Test
    fun `should not create user with invalid cpf`() {
        val createUserRequestDTO = CreateUserRequestDTO(
                firstName = "Test",
                lastName = "Test",
                email = "test@test.com",
                password = "123456",
                passwordConfirmation = "123456",
                cpf = "1234567890"
        )

        try {
            userService.createUser(createUserRequestDTO)
        } catch (e: Exception) {
            assertEquals("Invalid CPF", e.message)
        }
    }
}