package br.inatel.investminder.services

import br.inatel.investminder.controllers.dtos.request.BuyAssetsRequestDTO
import br.inatel.investminder.entities.UserAssets
import br.inatel.investminder.repositories.UserAssetRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserAssetsService(
        private val userAssetRepository: UserAssetRepository,
        private val financialAssetService: FinancialAssetService,
        private val userService: UserService
) {

    fun buyAsset(buyAssetsRequestDTO: BuyAssetsRequestDTO): UserAssets {
        val user = userService.findById(buyAssetsRequestDTO.userId)
        val financialAsset = financialAssetService.getAssetById(buyAssetsRequestDTO.financialAssetId.toInt())

        if (buyAssetsRequestDTO.userAssetId != 0L) {
            return buyExistingAsset(buyAssetsRequestDTO)
        }

        val userAsset = UserAssets(
                userId = user.id!!.toLong(),
                financialAssetId = financialAsset.get().id!!.toLong(),
                quantity = buyAssetsRequestDTO.quantity,
                totalSpend = buyAssetsRequestDTO.quantity * buyAssetsRequestDTO.price,
                totalProfit = 0.0,
                totalProfitPercent = 0.0,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
        )

        return userAssetRepository.save(userAsset)
    }

    fun buyExistingAsset(buyAssetsRequestDTO: BuyAssetsRequestDTO): UserAssets {
        val userAsset = userAssetRepository.findById(buyAssetsRequestDTO.userAssetId.toLong())
        val financialAsset = financialAssetService.getAssetById(buyAssetsRequestDTO.financialAssetId.toInt())

        val userAssetUpdated = UserAssets(
                id = userAsset.get().id,
                userId = userAsset.get().userId,
                financialAssetId = financialAsset.get().id!!.toLong(),
                quantity = userAsset.get().quantity + buyAssetsRequestDTO.quantity,
                totalSpend = userAsset.get().totalSpend + (buyAssetsRequestDTO.quantity * buyAssetsRequestDTO.price),
                totalValue = calculateTotalValue(userAsset.get()) + (buyAssetsRequestDTO.quantity * buyAssetsRequestDTO.price),
                totalProfit = calculateTotalProfit(userAsset.get()),
                totalProfitPercent = calculateTotalProfitPercent(userAsset.get()),
                createdAt = userAsset.get().createdAt,
                updatedAt = LocalDateTime.now()
        )

        return userAssetRepository.save(userAssetUpdated)
    }

    fun updateAssetById(id: Int): UserAssets {
        val userAsset = userAssetRepository.findById(id.toLong())

        val userAssetUpdated = UserAssets(
                id = userAsset.get().id,
                userId = userAsset.get().userId,
                financialAssetId = userAsset.get().financialAssetId,
                quantity = userAsset.get().quantity,
                totalSpend = userAsset.get().totalSpend,
                totalValue = calculateTotalValue(userAsset.get()),
                totalProfit = calculateTotalProfit(userAsset.get()),
                totalProfitPercent = calculateTotalProfitPercent(userAsset.get()),
                createdAt = userAsset.get().createdAt,
                updatedAt = LocalDateTime.now()
        )

        return userAssetRepository.save(userAssetUpdated)
    }

    fun getAllAssetsUser(userId: Long): List<UserAssets> {
        val assets = userAssetRepository.findByUserId(userId)
        return assets.map { asset -> updateAssetById(asset.id.toInt()) }
    }
    private fun calculateTotalValue(userAsset: UserAssets): Double {
        return userAsset.quantity * financialAssetService.getAssetById(userAsset.financialAssetId.toInt()).get().price
    }

    private fun calculateTotalProfit(userAsset: UserAssets): Double {
        return calculateTotalValue(userAsset) - userAsset.totalSpend
    }

    private fun calculateTotalProfitPercent(userAsset: UserAssets): Double {
        return (calculateTotalProfit(userAsset) / userAsset.totalSpend) * 100
    }


}