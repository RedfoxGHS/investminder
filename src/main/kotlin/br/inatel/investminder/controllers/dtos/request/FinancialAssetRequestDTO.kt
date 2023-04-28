package br.inatel.investminder.controllers.dtos.request

class FinancialAssetRequestDTO(
    var name: String,
    var type: String,
    var price: Double,
    var company: String
)