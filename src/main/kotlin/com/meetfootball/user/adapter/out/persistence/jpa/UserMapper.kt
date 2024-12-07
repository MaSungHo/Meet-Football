package com.meetfootball.user.adapter.out.persistence.jpa

import com.meetfootball.user.application.domain.entity.User

class UserMapper {
    companion object {
        fun mapToDomainEntity(jpaUser: com.meetfootball.user.adapter.out.persistence.jpa.User) = User(
            jpaUser.name,
            jpaUser.email,
            jpaUser.password
        )
    }
}