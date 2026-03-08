package com.meetfootball.adapter.user.out.persistence.jpa

import com.meetfootball.adapter.base.Persistence
import com.meetfootball.application.user.domain.entity.UserEntity
import com.meetfootball.application.user.port.out.LoadUserPort
import org.springframework.data.repository.findByIdOrNull

@Persistence
class FindUserByIdAdapter(
    private val jpaUserRepository: UserRepository
) : LoadUserPort {
    override fun findById(id: Long): UserEntity? {
        return jpaUserRepository.findByIdOrNull(id)?.let { UserMapper.toDomainEntity(it) }
    }
}
