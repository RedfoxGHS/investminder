package br.inatel.investminder.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity(name = "user_assets")
class UserAssets (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,
        var userId: Long = 0,
        var financialAssetId: Long = 0,
        var quantity: Int = 0,
        var totalSpend: Double = 0.0,
        var totalValue: Double = 0.0,
        var totalProfit: Double = 0.0,
        var totalProfitPercent: Double = 0.0,
        var createdAt: LocalDateTime = LocalDateTime.now(),
        var updatedAt: LocalDateTime = LocalDateTime.now(),
        @ManyToOne
        @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
        var user: User? = null
)