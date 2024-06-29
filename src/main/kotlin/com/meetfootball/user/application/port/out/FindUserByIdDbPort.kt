package com.meetfootball.user.application.port.out

import com.meetfootball.user.application.domain.entity.User

interface FindUserByIdDbPort {
    fun findUserById(id: Long): User?
}
