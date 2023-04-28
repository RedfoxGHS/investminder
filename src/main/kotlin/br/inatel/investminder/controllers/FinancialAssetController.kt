package br.inatel.investminder.controllers

import br.inatel.investminder.controllers.dtos.request.FinancialAssetRequestDTO
import br.inatel.investminder.entities.FinancialAsset
import br.inatel.investminder.services.FinancialAssetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/financial-assets")
class FinancialAssetController(
        private val financialAssetService: FinancialAssetService,
) {

    @PostMapping
    fun createAsset(@RequestBody financialAssetRequestDTO: FinancialAssetRequestDTO): ResponseEntity<FinancialAsset> {
        return ResponseEntity.status(HttpStatus.CREATED).body(financialAssetService.createAsset(financialAssetRequestDTO))
    }

    @GetMapping
    fun getAllAssets(): ResponseEntity<List<FinancialAsset>> {
        return ResponseEntity.ok(financialAssetService.getAllAssets())
    }
}