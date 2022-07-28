package com.joserobertofilho.personia.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<BusinessException> {

        val errorMessage = BusinessException(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleHierarchyWithLoopException(ex: HierarchyWithLoopException): ResponseEntity<BusinessException> {
        val errorMessage = BusinessException(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleDuplicatedEmployeeException(ex: HttpMessageNotReadableException): ResponseEntity<BusinessException> {
        val errorMessage = BusinessException(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleMultipleSeniorSupervisorsException(ex: MultipleSeniorEmployeeFoundException): ResponseEntity<BusinessException> {
        val errorMessage = BusinessException(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleEmployeeNotFoundException(ex: EmployeeNotFoundException): ResponseEntity<BusinessException> {
        val errorMessage = BusinessException(
            HttpStatus.NOT_FOUND.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}