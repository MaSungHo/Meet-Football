package com.meetfootball.adapter.user.`in`.web.response

import com.meetfootball.adapter.base.`in`.web.response.ApiResponse
import com.meetfootball.application.user.domain.entity.UserEntity

data class FindUserByIdResponse(
    val name: String,
    val email: String,
) : ApiResponse() {
    constructor(user: UserEntity) : this(
        name = user.name,
        email = user.email,
    )
}
