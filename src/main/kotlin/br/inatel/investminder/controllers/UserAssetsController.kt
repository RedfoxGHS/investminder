package br.inatel.investminder.controllers

import br.inatel.investminder.controllers.dtos.request.BuyAssetsRequestDTO
import br.inatel.investminder.controllers.dtos.request.FinancialAssetRequestDTO
import br.inatel.investminder.entities.FinancialAsset
import br.inatel.investminder.entities.UserAssets
import br.inatel.investminder.services.FinancialAssetService
import br.inatel.investminder.services.UserAssetsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user-assets")
class UserAssetsController(
        private val userAssetsService: UserAssetsService
) {

    @PostMapping("buy")
    fun buyAsset(@RequestBody buyAssetsRequestDTO: BuyAssetsRequestDTO): ResponseEntity<UserAssets> {
        val buyAssetsResponse = userAssetsService.buyAsset(buyAssetsRequestDTO)
        return ResponseEntity(buyAssetsResponse, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getAllAssetsUser(@PathVariable id: Long): ResponseEntity<List<UserAssets>> {
        return ResponseEntity.ok(userAssetsService.getAllAssetsUser(id))
    }

}