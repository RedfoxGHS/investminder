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

        val userAssets= userAssetRepository.findByFinancialAssetId(buyAssetsRequestDTO.financialAssetId)

        if (userAssets != null) {
            return buyExistingAsset(buyAssetsRequestDTO, userAssets)
        }

        val financialAsset = financialAssetService.getAssetById(buyAssetsRequestDTO.financialAssetId.toInt())

        val userAsset = UserAssets(
                userId = user.id!!.toLong(),
                financialAssetId = financialAsset.get().id!!.toLong(),
                name = financialAsset.get().name,
                quantity = buyAssetsRequestDTO.quantity,
                totalSpend = buyAssetsRequestDTO.quantity * buyAssetsRequestDTO.price,
                totalValue = buyAssetsRequestDTO.price * buyAssetsRequestDTO.quantity,
                totalProfit = 0.0,
                totalProfitPercent = 0.0,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
        )

        return userAssetRepository.save(userAsset)
    }

    fun buyExistingAsset(buyAssetsRequestDTO: BuyAssetsRequestDTO, userAsset: UserAssets): UserAssets {

        val financialAsset = financialAssetService.getAssetById(buyAssetsRequestDTO.financialAssetId.toInt())

        if (!financialAsset.isPresent) {
            throw  AccountNotFoundException("Financial asset not found")
        }

        userAsset.quantity = userAsset.quantity + buyAssetsRequestDTO.quantity
        userAsset.totalSpend = userAsset.totalSpend + (buyAssetsRequestDTO.quantity * buyAssetsRequestDTO.price)
        userAsset.totalValue = buyAssetsRequestDTO.price * userAsset.quantity
        userAsset.updatedAt = LocalDateTime.now()
        userAsset.totalProfit = userAsset.totalValue - userAsset.totalSpend
        userAsset.totalProfitPercent = (userAsset.totalProfit / userAsset.totalSpend) * 100

        financialAsset.get().price = buyAssetsRequestDTO.price
        financialAssetService.update(financialAsset.get())

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