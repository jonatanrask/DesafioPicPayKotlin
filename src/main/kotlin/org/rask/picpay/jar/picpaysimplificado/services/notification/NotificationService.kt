package org.rask.picpay.jar.picpaysimplificado.services.notification

import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.Transaction
import org.springframework.stereotype.Service

@Service
class NotificationService(notificationProducer: NotificationProducer) {
    private val notificationProducer = notificationProducer

    fun notify(transaction: Transaction) {
        notificationProducer.sendNotification(transaction)
    }

}