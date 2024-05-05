package org.rask.picpay.jar.picpaysimplificado.controllers

import org.rask.picpay.jar.picpaysimplificado.domain.entities.user.UserDTO
import org.rask.picpay.jar.picpaysimplificado.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<UserDTO>> {
        return ResponseEntity.ok(userService.findAllPaged(pageable))
    }
    @GetMapping("/id/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<UserDTO> {
        return ResponseEntity.ok().body(userService.findById(id))
    }
    @GetMapping("/email/{email}")
    fun findByEmail(@PathVariable email: String): ResponseEntity<UserDTO> {
        return ResponseEntity.ok().body(userService.findByEmail(email))
    }
    @GetMapping("/document/{document}")
    fun findByDocument(@PathVariable document: String): ResponseEntity<UserDTO> {
        return ResponseEntity.ok().body(userService.findByDocument(document))
    }
    @PostMapping
    fun insert(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        return ResponseEntity
            .created(ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("{/id}")
                .buildAndExpand(userDTO.id)
                .toUri())
            .body(userService.insert(userDTO))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        return ResponseEntity.ok().body(userService.update(id, userDTO))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<UserDTO> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }
}