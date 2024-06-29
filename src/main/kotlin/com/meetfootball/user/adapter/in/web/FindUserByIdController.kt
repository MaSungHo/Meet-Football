package com.meetfootball.user.adapter.`in`.web

import com.meetfootball.common.annotation.WebAdapter
import com.meetfootball.common.response.ApiResponse
import com.meetfootball.common.response.ExceptionResponse
import com.meetfootball.user.adapter.`in`.web.response.FindUserByIdResponse
import com.meetfootball.user.application.domain.entity.ResultEntity
import com.meetfootball.user.application.domain.entity.User
import com.meetfootball.user.application.port.`in`.FindUserByIdUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@WebAdapter(path = "/api/users")
class FindUserByIdController(
    private val findUserByIdUseCase: FindUserByIdUseCase
) {
    @GetMapping("/by-id/{id}")
    fun findUserById(@PathVariable id: Long): ResponseEntity<ApiResponse<User>> {
        return when (val result = this.findUserByIdUseCase.findUserById(id)) {
            is ResultEntity.Success<User> -> ResponseEntity(FindUserByIdResponse(result), HttpStatus.OK)
            is ResultEntity.Fail<User> -> ResponseEntity(ExceptionResponse(result), HttpStatus.valueOf(result.error.statusCode))
        }
    }
}
