package org.rask.picpay.jar.picpaysimplificado.controllers

import org.rask.picpay.jar.picpaysimplificado.domain.entities.transaction.TransactionDTO
import org.rask.picpay.jar.picpaysimplificado.services.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("/transactions")
class TransactionController {
    @Autowired
    private lateinit var transactionService: TransactionService

    @GetMapping
    fun findAll(pageable: Pageable): ResponseEntity<Page<TransactionDTO>> {
        return ResponseEntity.ok(transactionService.findAllPaged(pageable))
    }
    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<TransactionDTO> {
        return ResponseEntity.ok().body(transactionService.findById(id))
    }
    @PostMapping
    fun insert(@RequestBody transactionDTO: TransactionDTO): ResponseEntity<TransactionDTO> {
        return ResponseEntity
            .created(ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("{id}")
                .buildAndExpand(transactionDTO.id)
                .toUri())
            .body(transactionService.insert(transactionDTO))
    }
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<TransactionDTO> {
        transactionService.delete(id)
        return ResponseEntity.noContent().build()
    }
}