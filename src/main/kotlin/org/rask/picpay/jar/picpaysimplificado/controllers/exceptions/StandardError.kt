package org.rask.picpay.jar.picpaysimplificado.controllers.exceptions

import java.time.LocalDateTime

data class StandardError(
    val timeStamp: LocalDateTime,
    val status: Int,
    val message: String?,
    val error: String,
    val path: String
    )



