package org.rask.picpay.jar.picpaysimplificado.services.notification

import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.Transaction
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class NotificationProducer {
    private lateinit var kafkaTemplate: KafkaTemplate<String, Transaction>

    constructor(kafkaTemplate: KafkaTemplate<String, Transaction>) {
        this.kafkaTemplate = kafkaTemplate
    }

    fun sendNotification(transaction: Transaction) {
      kafkaTemplate.send("transaction-notification", transaction)
    }

}