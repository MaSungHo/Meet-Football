package com.meetfootball.user.adapter.out.persistence.jpa

import com.meetfootball.common.adapter.Persistence
import com.meetfootball.user.application.domain.entity.User
import com.meetfootball.user.application.port.out.FindUserByIdDbPort
import org.springframework.data.repository.findByIdOrNull

@Persistence
class FindUserByIdAdapter(
    private val jpaUserRepository: UserRepository
): FindUserByIdDbPort {
    override fun findUserById(id: Long): User? {
        val jpaUser = this.jpaUserRepository.findByIdOrNull(id)

        return jpaUser?.let { UserMapper.mapToDomainEntity(it) }
    }
}