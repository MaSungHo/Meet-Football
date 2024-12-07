package com.meetfootball.application.user.port.out

import com.meetfootball.application.user.domain.entity.UserEntity

interface FindUserByIdDbPort {
    fun findUserById(id: Long): UserEntity?
}
