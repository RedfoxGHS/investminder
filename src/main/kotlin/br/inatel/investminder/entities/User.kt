package br.inatel.investminder.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "users")
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String,
        val cpf: String,
        var balance: Double = 0.0,
        @OneToMany(
                mappedBy = "user",
                fetch = jakarta.persistence.FetchType.LAZY,
                cascade = [CascadeType.PERSIST]
        )
        @JsonBackReference
        var userAssets: MutableList<UserAssets> = mutableListOf()
) {

    fun addAsset(userAsset: UserAssets) {
        userAssets.add(userAsset)
    }

    fun removeAsset(userAsset: UserAssets) {
        userAssets.remove(userAsset)
    }

    fun updateBalance() {
        var total = 0.0
        userAssets.forEach {
            total += it.totalValue
        }
        balance = total
    }
    override fun toString(): String {
        return "User(id='$id', firstName='$firstName', lastName='$lastName', email='$email', password='$password', CPF='$cpf')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        return cpf == other.cpf
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + cpf.hashCode()
        return result
    }


}