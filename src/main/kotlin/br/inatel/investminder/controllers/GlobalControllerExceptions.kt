package br.inatel.investminder.controllers

import br.inatel.investminder.exceptions.AccountAlreadyExistException
import br.inatel.investminder.exceptions.CreateAccountException
import br.inatel.investminder.exceptions.FinancialAssetNotFoundException
import br.inatel.investminder.exceptions.StandardError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.Instant

@ControllerAdvice
class GlobalControllerExceptions : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(CreateAccountException::class)])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerBadRequest(e: Exception, request: WebRequest): ResponseEntity<StandardError> {
        val error = StandardError()
        error.setTimestamp(Instant.now().toString())
        error.setStatus(HttpStatus.BAD_REQUEST.value())
        error.setError(HttpStatus.BAD_REQUEST.reasonPhrase)
        error.setMessage(e.localizedMessage)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }

    @ExceptionHandler(value = [(AccountAlreadyExistException::class)])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handlerConflict(e: Exception, request: WebRequest): ResponseEntity<StandardError> {
        val error = StandardError()
        error.setTimestamp(Instant.now().toString())
        error.setStatus(HttpStatus.CONFLICT.value())
        error.setError(HttpStatus.CONFLICT.reasonPhrase)
        error.setMessage(e.localizedMessage)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error)
    }

    @ExceptionHandler(value = [(FinancialAssetNotFoundException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlerNotFound(e: Exception, request: WebRequest): ResponseEntity<StandardError> {
        val error = StandardError()
        error.setTimestamp(Instant.now().toString())
        error.setStatus(HttpStatus.NOT_FOUND.value())
        error.setError(HttpStatus.NOT_FOUND.reasonPhrase)
        error.setMessage(e.localizedMessage)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }
}