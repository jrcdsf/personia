package com.joserobertofilho.personia.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<StatusMessage> {

        val errorStatusMessage = StatusMessage(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorStatusMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleHierarchyWithLoopException(ex: HierarchyWithLoopException): ResponseEntity<StatusMessage> {
        val errorStatusMessage = StatusMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorStatusMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleDuplicatedEmployeeException(ex: HttpMessageNotReadableException): ResponseEntity<StatusMessage> {
        val errorStatusMessage = StatusMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorStatusMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleMultipleSeniorSupervisorsException(ex: MultipleSeniorEmployeesFoundException): ResponseEntity<StatusMessage> {
        val errorStatusMessage = StatusMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorStatusMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleEmployeeNotFoundException(ex: EmployeeNotFoundException): ResponseEntity<StatusMessage> {
        val errorStatusMessage = StatusMessage(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorStatusMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleSeniorSupervisorNotFoundException(ex: SeniorSupervisorNotFoundException): ResponseEntity<StatusMessage> {
        val errorStatusMessage = StatusMessage(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorStatusMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleEmptyRelationshipsException(ex: EmptyRelationshipsException): ResponseEntity<StatusMessage> {
        val errorStatusMessage = StatusMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorStatusMessage, HttpStatus.BAD_REQUEST)
    }
}