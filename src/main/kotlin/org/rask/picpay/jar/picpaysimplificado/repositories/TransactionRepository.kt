package org.rask.picpay.jar.picpaysimplificado.repositories

import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID> {
}