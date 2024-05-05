package org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction

import org.rask.picpay.jar.picpaysimplificado.domain.entities.user.User
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class TransactionDTO(
    val id: UUID?,
    val userPayer: User,
    val userPayee: User,
    val balance: BigDecimal?,
    val createdAt: LocalDateTime?
    )
{
    constructor(transaction: Transaction) : this(
        id = transaction.id,
        userPayer = transaction.userPayer,
        userPayee = transaction.userPayee,
        balance = transaction.balance,
        createdAt = transaction.createdAt
    )
}

