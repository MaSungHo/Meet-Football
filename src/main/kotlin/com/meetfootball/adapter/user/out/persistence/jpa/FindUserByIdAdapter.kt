package com.meetfootball.adapter.user.out.persistence.jpa

import com.meetfootball.adapter.base.Persistence
import com.meetfootball.application.user.domain.entity.UserEntity
import com.meetfootball.application.user.port.out.FindUserByIdDbPort
import org.springframework.data.repository.findByIdOrNull

@Persistence
class FindUserByIdAdapter(
    private val jpaUserRepository: UserRepository
): FindUserByIdDbPort {
    override fun findUserById(id: Long): UserEntity? {
        val jpaUser = this.jpaUserRepository.findByIdOrNull(id)

        return jpaUser?.let { UserMapper.toDomainEntity(it) }
    }
}