package br.inatel.investminder.services

import br.inatel.investminder.controllers.dtos.request.FinancialAssetRequestDTO
import br.inatel.investminder.entities.FinancialAsset
import br.inatel.investminder.repositories.FinancialAssetRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class FinancialAssetService(private val financialAssetRepository: FinancialAssetRepository) {

    fun createAsset(financialAssetRequestDTO: FinancialAssetRequestDTO): FinancialAsset{
        val financialAsset = FinancialAsset(
            name = financialAssetRequestDTO.name,
            type = financialAssetRequestDTO.type,
            price = financialAssetRequestDTO.price,
            company = financialAssetRequestDTO.company
        )
        return financialAssetRepository.save(financialAsset)
    }

    fun getAllAssets(): List<FinancialAsset> {
        return financialAssetRepository.findAll()
    }

    fun getAssetById(id: Int): Optional<FinancialAsset> {
        return financialAssetRepository.findById(id.toLong())
    }

    fun getAssetByName(name: String): List<FinancialAsset> {
        return financialAssetRepository.findByNameContaining(name)
    }
}