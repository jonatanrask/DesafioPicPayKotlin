package org.rask.picpay.jar.picpaysimplificado.services

import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.Transaction
import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.TransactionDTO
import org.rask.picpay.jar.picpaysimplificado.repositories.TransactionRepository
import org.rask.picpay.jar.picpaysimplificado.repositories.UserRepository
import org.rask.picpay.jar.picpaysimplificado.services.exceptions.DatabaseException
import org.rask.picpay.jar.picpaysimplificado.services.exceptions.MerchantCannotSendMoneyException
import org.rask.picpay.jar.picpaysimplificado.services.exceptions.ResourceNotFoundException
import org.rask.picpay.jar.picpaysimplificado.services.exceptions.WithoutBalanceTransferException
import org.rask.picpay.jar.picpaysimplificado.services.notification.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TransactionService {
    @Autowired
    private lateinit var transactionRepository: TransactionRepository
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var authorizationService: AuthorizationService
    @Autowired
    private lateinit var notificationService: NotificationService

    @Transactional(readOnly = true)
    fun findAllPaged(pageable: Pageable): Page<TransactionDTO> {
        return transactionRepository.findAll(pageable).map { TransactionDTO(it) }
    }
    @Transactional(readOnly = true)
    fun findById(id: UUID): TransactionDTO {
        val transaction = transactionRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Transaction with id $id not found") }
        return TransactionDTO(transaction)
    }
    @Transactional
    fun insert(transactionDTO: TransactionDTO): TransactionDTO {
        val transaction = Transaction()
        createTransaction(transaction, transactionDTO)
        setNewBalance(transaction)
        validTransaction(transaction)
        authorizationService.authorize(transaction)
        transactionRepository.save(transaction)
        notificationService.notify(transaction)
        return TransactionDTO(transaction)
    }

    fun delete(id: UUID) {
        try {
            transactionRepository.deleteById(id)
        } catch (e: DataIntegrityViolationException) {
            throw DatabaseException("Data error occurred while trying to delete a transaction! $e")
        }
    }
    private fun createTransaction(
        transaction: Transaction,
        transactionDTO: TransactionDTO
    ) {
        transaction.id = transactionDTO.id
        transaction.balance = transactionDTO.balance
        transaction.userPayee = userRepository.findById(transactionDTO.userPayee.id!!).get()
        transaction.userPayer = userRepository.findById(transactionDTO.userPayer.id!!).get()
        transaction.balance = transactionDTO.balance
        transaction.createdAt = transactionDTO.createdAt
    }

    private fun setNewBalance(transaction: Transaction) {
        transaction.userPayee.balance += transaction.balance!!
        transaction.userPayer.balance -= transaction.balance!!
        userRepository.save(transaction.userPayer)
        userRepository.save(transaction.userPayee)
    }

    private fun validTransaction(transaction: Transaction) {
        if (!transaction.userPayer.userType?.let { transaction.userPayer.canSendMoney(it) }!!) {
            throw MerchantCannotSendMoneyException("Merchant only receive transfers!")
        }
        if (!transaction.userPayer.hasNecessaryBalance(transaction.userPayer.balance)) {
            throw WithoutBalanceTransferException("User has no balance available for transfer")
        }
    }

}