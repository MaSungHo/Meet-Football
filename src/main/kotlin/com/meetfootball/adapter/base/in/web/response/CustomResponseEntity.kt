package com.meetfootball.adapter.base.`in`.web.response

import com.meetfootball.application.base.Result
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CustomResponseEntity {
    companion object {
        fun <T> toResponse(
            result: Result<T>,
            successMapper: (T) -> ApiResponse,
        ): ResponseEntity<ApiResponse> {
            return when (result) {
                is Result.Success -> ResponseEntity(successMapper(result.data), HttpStatus.OK)
                is Result.Fail -> ResponseEntity(ExceptionResponse(result), HttpStatus.valueOf(result.error.statusCode))
            }
        }
    }
}
