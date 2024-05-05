package org.rask.picpay.jar.picpaysimplificado.services.exceptions

class MerchantCannotSendMoneyException : RuntimeException {
    constructor(message: String) : super(message)
}