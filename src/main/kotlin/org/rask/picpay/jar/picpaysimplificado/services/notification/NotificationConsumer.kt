package org.rask.picpay.jar.picpaysimplificado.services.notification

import org.rask.picpay.jar.picpaysimplificado.domain.entities.notification.Notification
import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.Transaction
import org.rask.picpay.jar.picpaysimplificado.services.exceptions.NotificationException
import org.springframework.http.ResponseEntity
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class NotificationConsumer {
    private lateinit var restClient: RestClient

    constructor(builderRestClient: RestClient.Builder){
        this.restClient = builderRestClient
            .baseUrl("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6").
            build()
    }
    @KafkaListener(topicPattern = "transaction-notification", groupId = "picpay-simplificado")
    fun receiveNotification(transaction: Transaction){
        val response = getResponse()
        validResponse(response)

    }

    private fun validResponse(response: ResponseEntity<Notification>) {
        if (response.statusCode.isError || !response.body?.isNotified!!) {
            throw NotificationException("Error seeding notification")
        }
    }

    private fun getResponse() = restClient.get().retrieve().toEntity(Notification::class.java)
}