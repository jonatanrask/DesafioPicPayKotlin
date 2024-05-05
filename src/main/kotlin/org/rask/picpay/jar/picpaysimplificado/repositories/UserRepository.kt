package org.rask.picpay.jar.picpaysimplificado.repositories

import org.rask.picpay.jar.picpaysimplificado.domain.entities.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): Optional<User>
    fun findByDocument(document: String): Optional<User>
}