package com.meetfootball.application.user.domain.service

import com.meetfootball.application.base.Error
import com.meetfootball.application.base.Result
import com.meetfootball.application.base.UseCase
import com.meetfootball.application.user.domain.entity.UserEntity
import com.meetfootball.application.user.port.`in`.FindUserByIdUseCase
import com.meetfootball.application.user.port.out.LoadUserPort

@UseCase
class FindUserByIdService(
    private val loadUserPort: LoadUserPort
): FindUserByIdUseCase {
    override fun findUserById(id: Long): Result<UserEntity> {
        val user = loadUserPort.findById(id)
        return if (user != null) Result.Success(user) else Result.Fail(Error.NotFound)
    }
}
