package com.meetfootball.user.adapter.`in`.web.response

import com.meetfootball.common.response.ApiResponse
import com.meetfootball.user.application.domain.entity.ResultEntity
import com.meetfootball.user.application.domain.entity.User

data class FindUserByIdResponse(
    private val result: ResultEntity.Success<User>
): ApiResponse<User>(result) {
    val name: String = result.data.name

    val email: String = result.data.email
}
