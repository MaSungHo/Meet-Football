package com.meetfootball.application.user.port.`in`

import com.meetfootball.application.base.Result
import com.meetfootball.application.user.domain.entity.UserEntity

interface FindUserByIdUseCase {
    fun findUserById(id: Long): Result<UserEntity>
}
