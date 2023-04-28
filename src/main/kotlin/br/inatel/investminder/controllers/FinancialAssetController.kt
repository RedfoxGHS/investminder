package br.inatel.investminder.controllers

import br.inatel.investminder.controllers.dtos.request.FinancialAssetRequestDTO
import br.inatel.investminder.entities.FinancialAsset
import br.inatel.investminder.services.FinancialAssetService
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
import java.util.Optional

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

    @GetMapping("/{id}")
    fun getAssetById(@PathVariable id: Int): ResponseEntity<FinancialAsset> {
        val asset: Optional<FinancialAsset> = financialAssetService.getAssetById(id)
        return if (!asset.isPresent) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(asset.get())
        }
    }

    @GetMapping("/name/{name}")
    fun getAssetByName(@PathVariable name: String): ResponseEntity<List<FinancialAsset>> {
        return ResponseEntity.ok(financialAssetService.getAssetByName(name))
    }

    @GetMapping("/type/{type}")
    fun getAssetByType(@PathVariable type: String): ResponseEntity<List<FinancialAsset>> {
        return ResponseEntity.ok(financialAssetService.getAssetByType(type))
    }

    @PutMapping("/{id}")
    fun updateAssetById(@PathVariable id: Int, @RequestBody financialAssetRequestDTO: FinancialAssetRequestDTO): ResponseEntity<FinancialAsset> {
        return ResponseEntity.ok(financialAssetService.updateAssetById(id, financialAssetRequestDTO))
    }

    @DeleteMapping("/{id}")
    fun deleteAssetById(@PathVariable id: Int): ResponseEntity<FinancialAsset> {
        financialAssetService.deleteAssetById(id)
        return ResponseEntity.noContent().build()
    }
}