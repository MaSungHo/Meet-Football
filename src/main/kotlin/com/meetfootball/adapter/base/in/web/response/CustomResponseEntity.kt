package com.meetfootball.adapter.base.`in`.web.response

import com.meetfootball.application.base.Result
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CustomResponseEntity {
    companion object {
        inline fun <reified T, reified U: ApiResponse> toResponse(
            result: Result<T>
        ): ResponseEntity<ApiResponse> {
            return when (result) {
                is Result.Success<T> -> ResponseEntity(U::class.constructors.first().call(result), HttpStatus.OK)
                is Result.Fail<T> -> ResponseEntity(ExceptionResponse(result), HttpStatus.valueOf(result.error.statusCode))
            }
        }
    }
}
