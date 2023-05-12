package br.inatel.investminder.services

import br.inatel.investminder.controllers.dtos.request.BuyAssetsRequestDTO
import br.inatel.investminder.entities.UserAssets
import br.inatel.investminder.exceptions.AccountNotFoundException
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
        val userAssetOptional = userAssetRepository.findById(buyAssetsRequestDTO.userAssetId.toLong())

        if (!userAssetOptional.isPresent) {
            throw  AccountNotFoundException("User asset not found")
        }

        val userAsset = userAssetOptional.get()
        val financialAsset = financialAssetService.getAssetById(buyAssetsRequestDTO.financialAssetId.toInt())

        userAsset.quantity = userAsset.quantity + buyAssetsRequestDTO.quantity
        userAsset.totalSpend = userAsset.totalSpend + (buyAssetsRequestDTO.quantity * buyAssetsRequestDTO.price)
        userAsset.totalValue = financialAsset.get().price + (userAsset.quantity + buyAssetsRequestDTO.quantity)
        userAsset.updatedAt = LocalDateTime.now()
        userAsset.totalProfit = userAsset.totalValue - userAsset.totalSpend
        userAsset.totalProfitPercent = (userAsset.totalProfit / userAsset.totalSpend) * 100

        return userAssetRepository.save(userAsset)
    }

    fun updateAssetById(id: Int): UserAssets {
        val userAssetOptional = userAssetRepository.findById(id.toLong())

        if (!userAssetOptional.isPresent) {
            throw  AccountNotFoundException("User asset not found")
        }

        val userAsset = userAssetOptional.get()
        val financialAsset = financialAssetService.getAssetById(id)

        //TODO get the real price of the asset and update the totalValue
        userAsset.totalValue = financialAsset.get().price + userAsset.quantity
        userAsset.updatedAt = LocalDateTime.now()
        userAsset.totalProfit = userAsset.totalValue - userAsset.totalSpend
        userAsset.totalProfitPercent = (userAsset.totalProfit / userAsset.totalSpend) * 100

        return userAssetRepository.save(userAsset)
    }

    fun getUserAssets(userId: Long): List<UserAssets> {
        return userAssetRepository.findByUserId(userId)
    }

    fun getBalanceTotal(userId: Long): Double {
        val userAssets = userAssetRepository.findByUserId(userId)
        var balanceTotal = 0.0

        userAssets.forEach {
            balanceTotal += it.totalValue
        }

        return balanceTotal
    }


}