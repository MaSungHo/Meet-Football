package com.meetfootball.application.user.port.out

import com.meetfootball.application.user.domain.entity.UserEntity

interface LoadUserPort {
    fun findById(id: Long): UserEntity?
}
