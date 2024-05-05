package org.rask.picpay.jar.picpaysimplificado.services.exceptions

class WithoutBalanceTransferException : RuntimeException {
    constructor(message: String?) : super(message)
}