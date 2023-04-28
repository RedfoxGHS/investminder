package br.inatel.investminder.controllers.dtos.request

class CreateUserRequestDTO(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var passwordConfirmation: String,
    var cpf: String
)