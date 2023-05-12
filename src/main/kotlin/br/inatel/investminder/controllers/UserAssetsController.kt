package br.inatel.investminder.controllers

import br.inatel.investminder.controllers.dtos.request.BuyAssetsRequestDTO
import br.inatel.investminder.controllers.dtos.request.FinancialAssetRequestDTO
import br.inatel.investminder.entities.FinancialAsset
import br.inatel.investminder.entities.UserAssets
import br.inatel.investminder.services.FinancialAssetService
import br.inatel.investminder.services.UserAssetsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user-assets")
@CrossOrigin(origins = ["http://localhost:3000"], maxAge=3600, allowCredentials = "true")
class UserAssetsController(
        private val userAssetsService: UserAssetsService
) {

    @PostMapping("/buy")
    fun buyAsset(@RequestBody buyAssetsRequestDTO: BuyAssetsRequestDTO): ResponseEntity<UserAssets> {
        val buyAssetsResponse = userAssetsService.buyAsset(buyAssetsRequestDTO)
        return ResponseEntity(buyAssetsResponse, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getAllAssetsUser(@PathVariable id: Long): ResponseEntity<List<UserAssets>> {
        return ResponseEntity.ok(userAssetsService.getUserAssets(id))
    }

    @GetMapping("/balance-total/{id}")
    fun getBalanceTotal(@PathVariable id: Long): ResponseEntity<Double> {
        return ResponseEntity.ok(userAssetsService.getBalanceTotal(id))
    }

}