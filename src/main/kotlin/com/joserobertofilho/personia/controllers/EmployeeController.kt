package com.joserobertofilho.personia.controllers

import com.joserobertofilho.personia.domain.usecases.RelationshipUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/employees")
class EmployeeController {

    @Autowired
    lateinit var usecase: RelationshipUseCase

    @PostMapping
    fun createRelationships(@RequestBody relationships: Map<String, String>): ResponseEntity<String> {
        return if (usecase.createRelationships(relationships).isNotEmpty())
            ResponseEntity("Employees hierarchy successfully created", HttpStatus.CREATED)
        else ResponseEntity("API failure - cannot create the employees hierarchy", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping
    fun findEmployeeHierarchy(@RequestParam(required = false, defaultValue = "") name: String,): ResponseEntity<Map<String, Any>>{
        return if (name.isEmpty()) {
            ResponseEntity(usecase.getFullHierarchy(), HttpStatus.OK)
        } else {
            ResponseEntity(usecase.getSupervisorAndSeniorSupervisorByEmployee(name), HttpStatus.OK)
        }
    }

}