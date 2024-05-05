package org.rask.picpay.jar.picpaysimplificado.services.exceptions

class UnauthorizedTransactionException : RuntimeException {
    constructor(message: String) : super(message)
}