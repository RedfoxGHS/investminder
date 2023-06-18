package br.inatel.investminder.controllers.dtos.request

import br.inatel.investminder.entities.UserAssets

class BuyAssetsRequestDTO (
    var userId: Long = 0,
    var financialAssetId: Long = 0,
    var quantity: Int = 0,
    var price: Double = 0.0,
    var fee: Double = 0.0
)

/*
json
{
    "userId": 1,
    "userAssetId": 1,
    "financialAssetId": 1,
    "quantity": 1,
    "price": 1.0,
    "fee": 1.0
}
 */