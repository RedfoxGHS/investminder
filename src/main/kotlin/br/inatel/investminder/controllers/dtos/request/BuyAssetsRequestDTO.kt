package br.inatel.investminder.controllers.dtos.request

import br.inatel.investminder.entities.UserAssets

class BuyAssetsRequestDTO (
    var userId: Long = 0,
    var userAssetId: Long = 0,
    var financialAssetId: Long = 0,
    var quantity: Int = 0,
    var price: Double = 0.0,
    var fee: Double = 0.0
)
