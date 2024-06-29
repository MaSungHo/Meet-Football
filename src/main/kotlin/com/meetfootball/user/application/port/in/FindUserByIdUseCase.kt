package com.meetfootball.user.application.port.`in`

import com.meetfootball.user.application.domain.entity.ResultEntity
import com.meetfootball.user.application.domain.entity.User

interface FindUserByIdUseCase {
    fun findUserById(id: Long): ResultEntity<User>
}
