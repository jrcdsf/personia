package com.joserobertofilho.personia.controllers

import com.joserobertofilho.personia.domain.entities.Employee
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
    fun createRelationships(@RequestBody relationships: Map<String, String>): ResponseEntity<MutableMap<String, Set<String>>> {
        return ResponseEntity(usecase.createRelationships(relationships), HttpStatus.CREATED)
    }

    @GetMapping
    fun getHierarchyByEmployee(@RequestParam(required = false, defaultValue = "") name: String,): ResponseEntity<Set<Employee>>{
        return if (name.isEmpty()) {
            ResponseEntity(usecase.getFullHierarchy(), HttpStatus.OK)
        } else {
            ResponseEntity(usecase.getHierarchyPerEmployee(name), HttpStatus.OK)
        }
    }
}