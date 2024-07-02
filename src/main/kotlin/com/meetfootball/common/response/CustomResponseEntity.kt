package com.meetfootball.common.response

import com.meetfootball.user.application.domain.entity.ResultEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CustomResponseEntity {
    companion object {
        inline fun <reified T, reified U: ApiResponse> toResponse(
            result: ResultEntity<T>
        ): ResponseEntity<ApiResponse> {
            return when (result) {
                is ResultEntity.Success<T> -> ResponseEntity(U::class.constructors.first().call(result), HttpStatus.OK)
                is ResultEntity.Fail<T> -> ResponseEntity(ExceptionResponse(result), HttpStatus.valueOf(result.error.statusCode))
            }
        }
    }
}
