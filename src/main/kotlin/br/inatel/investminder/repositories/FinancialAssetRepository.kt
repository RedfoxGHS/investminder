package br.inatel.investminder.repositories

import br.inatel.investminder.entities.FinancialAsset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface FinancialAssetRepository : JpaRepository<FinancialAsset, Long> {
    fun findByNameContaining(name: String): List<FinancialAsset>
}