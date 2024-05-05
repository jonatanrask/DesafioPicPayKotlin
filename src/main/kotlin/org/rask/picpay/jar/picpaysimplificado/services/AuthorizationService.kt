package org.rask.picpay.jar.picpaysimplificado.services

import org.rask.picpay.jar.picpaysimplificado.domain.entities.authorization.Authorization
import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.Transaction
import org.rask.picpay.jar.picpaysimplificado.services.exceptions.UnauthorizedTransactionException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class AuthorizationService {
    private lateinit var restClient: RestClient

    constructor(builderRestClient: RestClient.Builder) {
        this.restClient = builderRestClient
            .baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
            .build()
    }

    fun authorize(transaction: Transaction) {
        val responseAuthorization = getResponseAuthorization()
        validAuthorization(responseAuthorization)
    }
    private fun getResponseAuthorization(): ResponseEntity<Authorization> {
        return restClient.get().retrieve().toEntity(Authorization::class.java)

    }
    private fun validAuthorization(responseAuthorization: ResponseEntity<Authorization>) {
        if (responseAuthorization.statusCode.isError || !responseAuthorization.body?.isAuthorized()!!) {
            return throw UnauthorizedTransactionException("Transaction not authorized!")
        }
    }
}