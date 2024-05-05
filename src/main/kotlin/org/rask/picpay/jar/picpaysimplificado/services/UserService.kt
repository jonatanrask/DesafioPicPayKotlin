package org.rask.picpay.jar.picpaysimplificado.services

import org.rask.picpay.jar.picpaysimplificado.domain.entities.user.User
import org.rask.picpay.jar.picpaysimplificado.domain.entities.user.UserDTO
import org.rask.picpay.jar.picpaysimplificado.repositories.UserRepository
import org.rask.picpay.jar.picpaysimplificado.services.exceptions.DatabaseException
import org.rask.picpay.jar.picpaysimplificado.services.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Transactional(readOnly = true)
    fun findAllPaged(pageable: Pageable): Page<UserDTO> {
        return userRepository.findAll(pageable).map(::UserDTO)
    }

    @Transactional(readOnly = true)
    fun findById(id: UUID): UserDTO {
        val user = userRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("User with ID $id not found") }
        return UserDTO(user)
    }
    @Transactional(readOnly = true)
    fun findByEmail(email: String): UserDTO {
        val user = userRepository
            .findByEmail(email).
            orElseThrow { ResourceNotFoundException("User with Email $email not found") }
        return UserDTO(user)
    }
    @Transactional(readOnly = true)
    fun findByDocument(document: String): UserDTO {
        val user = userRepository
            .findByDocument(document)
            .orElseThrow { ResourceNotFoundException("User with Document $document not found") }
        return UserDTO(user)
    }
    @Transactional
    fun insert(userDTO: UserDTO): UserDTO {
        var user = User()
        copyUserDTOToEntity(user, userDTO)
        userRepository.save(user)
        return UserDTO(user)
    }
    @Transactional
    fun update(id: UUID ,userDTO: UserDTO): UserDTO {
        try {
            val user = userRepository.getReferenceById(id)
            copyUserDTOToEntity(user, userDTO)
            userRepository.save(user)
            return UserDTO(user)
        } catch (e: JpaObjectRetrievalFailureException) {
            throw ResourceNotFoundException("User with ID $id not found")
        }
    }

    fun delete(id: UUID) {
        try {
            userRepository.deleteById(id)
        }
        catch (e: DataIntegrityViolationException) {
            throw DatabaseException("Data error occurred while trying to delete a user $e")
        }
    }

    private fun copyUserDTOToEntity(
        user: User,
        userDTO: UserDTO
    ) {
        user.id = userDTO.id
        user.name = userDTO.name
        user.email = userDTO.email
        user.document = userDTO.document
        user.balance = userDTO.balance
        user.password = userDTO.password
        user.userType = userDTO.userType
    }


}