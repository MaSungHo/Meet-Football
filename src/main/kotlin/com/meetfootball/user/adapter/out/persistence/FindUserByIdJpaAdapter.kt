package com.meetfootball.user.adapter.out.persistence

import com.meetfootball.common.annotation.PersistenceAdapter
import com.meetfootball.user.application.domain.entity.User
import com.meetfootball.user.application.port.out.FindUserByIdDbPort
import org.springframework.data.repository.findByIdOrNull

@PersistenceAdapter
class FindUserByIdJpaAdapter(
    private val jpaUserRepository: JpaUserRepository
): FindUserByIdDbPort {
    override fun findUserById(id: Long): User? {
        val jpaUser = this.jpaUserRepository.findByIdOrNull(id)

        return jpaUser?.let { UserMapper.mapToDomainEntity(it) }
    }
}