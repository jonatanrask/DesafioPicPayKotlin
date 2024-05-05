package org.rask.picpay.jar.picpaysimplificado.services.exceptions

class ResourceNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
}