package br.inatel.investminder.services

import br.inatel.investminder.controllers.dtos.request.BuyAssetsRequestDTO
import br.inatel.investminder.controllers.dtos.request.FinancialAssetRequestDTO
import br.inatel.investminder.entities.FinancialAsset
import br.inatel.investminder.exceptions.FinancialAssetNotFoundException
import br.inatel.investminder.repositories.FinancialAssetRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
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
        if (!financialAssetRepository.existsById(id.toLong())) {
            throw FinancialAssetNotFoundException()
        }
        return financialAssetRepository.findById(id.toLong())
    }

    fun getAssetByName(name: String): List<FinancialAsset> {
        return financialAssetRepository.findByNameContaining(name)
    }

    fun getAssetByType(type: String): List<FinancialAsset> {
        return financialAssetRepository.findByType(type)
    }

    fun updateAssetById(id: Int, financialAssetRequestDTO: FinancialAssetRequestDTO): FinancialAsset {
        val financialAsset = FinancialAsset(
            id = id,
            name = financialAssetRequestDTO.name,
            type = financialAssetRequestDTO.type,
            price = financialAssetRequestDTO.price,
            company = financialAssetRequestDTO.company
        )

        val financialAssetFound: Optional<FinancialAsset> = financialAssetRepository.findById(id.toLong())

        if (!financialAssetFound.isPresent) {
            throw FinancialAssetNotFoundException()
        }

        financialAssetFound.get().name = financialAsset.name
        financialAssetFound.get().type = financialAsset.type
        financialAssetFound.get().price = financialAsset.price
        financialAssetFound.get().company = financialAsset.company
        financialAssetFound.get().updateAt = LocalDateTime.now()
        return financialAssetRepository.save(financialAssetFound.get())
    }

    fun update(asset: FinancialAsset): FinancialAsset {
        return financialAssetRepository.save(asset)
    }

    fun deleteAssetById(id: Int) {
        val financialAssetFound: Optional<FinancialAsset> = financialAssetRepository.findById(id.toLong())

        if (!financialAssetFound.isPresent) {
            throw FinancialAssetNotFoundException()
        }

        financialAssetRepository.deleteById(id.toLong())
    }
}