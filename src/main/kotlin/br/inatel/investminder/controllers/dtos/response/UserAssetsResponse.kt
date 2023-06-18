package br.inatel.investminder.controllers.dtos.response

class UserAssetsResponse (
    var id: Long = 0,
    var name: String = "",
    var type: String = "",
    var price: Double = 0.0,
    var company: String = ""
)