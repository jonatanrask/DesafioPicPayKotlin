package org.rask.picpay.jar.picpaysimplificado.domain.entities.authorization

data class Authorization(
    val message: String
){
    fun isAuthorized() = message.equals("Autorizado")

}

