package org.rask.picpay.jar.picpaysimplificado.controllers.exceptions

import jakarta.servlet.http.HttpServletRequest
import org.rask.picpay.jar.picpaysimplificado.services.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class ResourceExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun entityNotFound(err: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<StandardError> {
        val standardError = StandardError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            err.message,
            "Resource not found",
            request.requestURL.toString()
        )
        return ResponseEntity.status(standardError.status).body(standardError)
    }
    @ExceptionHandler(DatabaseException::class)
    fun databaseViolation(err: DatabaseException, request: HttpServletRequest): ResponseEntity<StandardError> {
        val standardError = StandardError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            err.message,
            "Database violation",
            request.requestURL.toString()
        )
        return ResponseEntity.status(standardError.status).body(standardError)
    }
    @ExceptionHandler(MerchantCannotSendMoneyException::class)
    fun cannotSendMoney(err: MerchantCannotSendMoneyException, request: HttpServletRequest): ResponseEntity<StandardError> {
        val standardError = StandardError(
            LocalDateTime.now(),
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            err.message,
            "Cannot transfer",
            request.requestURL.toString()
        )
        return ResponseEntity.status(standardError.status).body(standardError)
    }

    @ExceptionHandler(WithoutBalanceTransferException::class)
    fun noBalanceAvailable(err: WithoutBalanceTransferException, request: HttpServletRequest): ResponseEntity<StandardError> {
        val standardError = StandardError(
            LocalDateTime.now(),
            HttpStatus.FORBIDDEN.value(),
            err.message,
            "Without balance",
            request.requestURL.toString()
        )
        return ResponseEntity.status(standardError.status).body(standardError)
    }

    @ExceptionHandler(UnauthorizedTransactionException::class)
    fun transactionUnauthorized(err: UnauthorizedTransactionException, request: HttpServletRequest): ResponseEntity<StandardError> {
        val standardError = StandardError(
            LocalDateTime.now(),
            HttpStatus.UNAUTHORIZED.value(),
            err.message,
            "Transaction unauthorized",
            request.requestURL.toString()
        )
        return ResponseEntity.status(standardError.status).body(standardError)
    }

    @ExceptionHandler(NotificationException::class)
    fun notificationError(err: NotificationException, request: HttpServletRequest): ResponseEntity<StandardError> {
        val standardError = StandardError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            err.message,
            "Error in notification service",
            request.requestURL.toString()
        )
        return ResponseEntity.status(standardError.status).body(standardError)
    }
}