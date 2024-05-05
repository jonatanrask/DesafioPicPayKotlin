package org.rask.picpay.jar.picpaysimplificado.domain.entities.user

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.rask.picpay.jar.picpaysimplificado.domain.entities.user.UserType.COMMON
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null
    var name: String = ""
    @Column(unique = true, nullable = false)
    var document: String = ""
    @Column(unique = true, nullable = false)
    var email: String = ""
    var password: String? = null
    var balance: BigDecimal = BigDecimal.ZERO
    var userType: UserType? = null

    constructor()

    constructor(user: User) {
        id = user.id
        name = user.name
        document = user.document
        email = user.email
        password = user.password
        balance = user.balance
        userType = user.userType
    }


    fun canSendMoney(userType: UserType): Boolean {
        return userType == COMMON
    }

    fun hasNecessaryBalance(valueToTransferred: BigDecimal): Boolean {
        return balance.compareTo(valueToTransferred) <= 0
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false
        if (document != other.document) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (balance != other.balance) return false
        if (userType != other.userType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + document.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (balance?.hashCode() ?: 0)
        result = 31 * result + (userType?.hashCode() ?: 0)
        return result
    }


}