package br.inatel.investminder.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class FinancialAsset(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val name: String,
    val type: String,
    val price: Double,
    val company: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updateAt: LocalDateTime = LocalDateTime.now()
) {
    override fun toString(): String {
        return "FinancialAsset(id=$id, name='$name', type='$type', price=$price, company='$company', createdAt=$createdAt, updateAt=$updateAt)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FinancialAsset

        if (name != other.name) return false
        if (type != other.type) return false
        return company == other.company
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + company.hashCode()
        return result
    }


}