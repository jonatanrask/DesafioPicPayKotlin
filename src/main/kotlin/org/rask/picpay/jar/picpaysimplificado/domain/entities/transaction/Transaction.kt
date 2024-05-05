package org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.rask.picpay.jar.picpaysimplificado.domain.entities.user.User
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "transactions")
class Transaction {
    @Id
    @GeneratedValue
    @UuidGenerator
    var id: UUID? = null
    var balance: BigDecimal? = null
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    var createdAt: LocalDateTime? = null
    @ManyToOne
    lateinit var userPayer: User
    @ManyToOne
    lateinit var userPayee: User

    constructor()

    constructor(transaction: Transaction) {
        id = transaction.id
        userPayer = transaction.userPayee
        userPayee = transaction.userPayee
        balance = transaction.balance
        createdAt = transaction.createdAt
    }

    @PrePersist
    fun prePersist(){
        this.createdAt = LocalDateTime.now()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transaction

        if (id != other.id) return false
        if (userPayer != other.userPayer) return false
        if (userPayee != other.userPayee) return false
        if (balance != other.balance) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (userPayer?.hashCode() ?: 0)
        result = 31 * result + (userPayee?.hashCode() ?: 0)
        result = 31 * result + (balance?.hashCode() ?: 0)
        result = 31 * result + createdAt.hashCode()
        return result
    }


}