package com.meetfootball.user.adapter.out.persistence

import com.meetfootball.user.application.domain.entity.User

class UserMapper {
    companion object {
        fun mapToDomainEntity(jpaUser: JpaUser) = User(
            jpaUser.name,
            jpaUser.email,
            jpaUser.password
        )
    }
}