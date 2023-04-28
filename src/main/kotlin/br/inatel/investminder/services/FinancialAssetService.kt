package br.inatel.investminder.services

import br.inatel.investminder.controllers.dtos.request.FinancialAssetRequestDTO
import br.inatel.investminder.entities.FinancialAsset
import br.inatel.investminder.repositories.FinancialAssetRepository
import org.springframework.stereotype.Service

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
}