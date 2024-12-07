package com.meetfootball.adapter.user.out.persistence.jpa

import com.meetfootball.application.user.domain.entity.UserEntity

class UserMapper {
    companion object {
        fun toDomainEntity(jpaUser: User) = UserEntity(
            jpaUser.name,
            jpaUser.email,
            jpaUser.password
        )
    }
}