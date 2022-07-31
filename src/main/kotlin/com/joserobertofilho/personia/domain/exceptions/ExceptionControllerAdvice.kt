package com.joserobertofilho.personia.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<Message> {

        val errorMessage = Message(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleHierarchyWithLoopException(ex: HierarchyWithLoopException): ResponseEntity<Message> {
        val errorMessage = Message(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleDuplicatedEmployeeException(ex: HttpMessageNotReadableException): ResponseEntity<Message> {
        val errorMessage = Message(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleMultipleSeniorSupervisorsException(ex: MultipleSeniorEmployeesFoundException): ResponseEntity<Message> {
        val errorMessage = Message(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleEmployeeNotFoundException(ex: EmployeeNotFoundException): ResponseEntity<Message> {
        val errorMessage = Message(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleSeniorSupervisorNotFoundException(ex: SeniorSupervisorNotFoundException): ResponseEntity<Message> {
        val errorMessage = Message(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleEmptyRelationshipsException(ex: EmptyRelationshipsException): ResponseEntity<Message> {
        val errorMessage = Message(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
}