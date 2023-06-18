package br.inatel.investminder.repositories

import br.inatel.investminder.entities.UserAssets
import org.apache.el.stream.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAssetRepository: JpaRepository<UserAssets, Long> {
    fun findByUserId(userId: Long): List<UserAssets>

    fun findByFinancialAssetId(financialAssetId: Long): UserAssets?
    fun findByUserIdAndFinancialAssetId(userId: Long, financialAssetId: Long): UserAssets?
}