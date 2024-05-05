package org.rask.picpay.jar.picpaysimplificado.domain.entities.user

import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.Transaction
import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.TransactionDTO
import java.math.BigDecimal
import java.util.UUID

data class UserDTO(
    val id: UUID?,
    val name: String,
    val document: String,
    val email: String,
    val password: String?,
    val balance: BigDecimal,
    val userType: UserType?,
)
{
    companion object {
        fun fromUser(user: User) = UserDTO(
            id = user.id,
            name = user.name,
            document = user.document,
            email = user.email,
            password = null,
            balance = user.balance,
            userType = user.userType
        )
    }

    constructor(user: User) : this(
        id = user.id,
        name = user.name,
        document = user.document,
        email = user.email,
        password = null,
        balance = user.balance,
        userType = user.userType
    )
}
