package com.meetfootball.adapter.user.`in`.web

import com.meetfootball.adapter.base.Web
import com.meetfootball.adapter.base.`in`.web.response.ApiResponse
import com.meetfootball.adapter.base.`in`.web.response.CustomResponseEntity
import com.meetfootball.adapter.user.`in`.web.response.FindUserByIdResponse
import com.meetfootball.application.user.domain.entity.UserEntity
import com.meetfootball.application.user.port.`in`.FindUserByIdUseCase
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
        return CustomResponseEntity.toResponse<UserEntity, FindUserByIdResponse>(result)
    }
}
