package com.meetfootball.user.adapter.`in`.web

import com.meetfootball.common.adapter.Web
import com.meetfootball.common.response.ApiResponse
import com.meetfootball.common.response.CustomResponseEntity
import com.meetfootball.user.adapter.`in`.web.response.FindUserByIdResponse
import com.meetfootball.user.application.domain.entity.User
import com.meetfootball.user.application.port.`in`.FindUserByIdUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Web(path = "/api/users")
class FindUserByIdController(
    private val findUserByIdUseCase: FindUserByIdUseCase
) {
    @GetMapping("/by-id/{id}")
    fun findUserById(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        val result = this.findUserByIdUseCase.findUserById(id)
        return CustomResponseEntity.toResponse<User, FindUserByIdResponse>(result)
    }
}
