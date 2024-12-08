package com.meetfootball.adapter.user.`in`.web.response

import com.meetfootball.adapter.base.`in`.web.response.ApiResponse
import com.meetfootball.application.base.Result
import com.meetfootball.application.user.domain.entity.UserEntity

data class FindUserByIdResponse(
    private val result: Result.Success<UserEntity>
): ApiResponse() {
    val name: String = result.data.name

    val email: String = result.data.email
}
