package com.meetfootball.user.application.domain.service

import com.meetfootball.common.adapter.UseCase
import com.meetfootball.user.application.domain.entity.ErrorEntity
import com.meetfootball.user.application.domain.entity.ResultEntity
import com.meetfootball.user.application.domain.entity.User
import com.meetfootball.user.application.port.`in`.FindUserByIdUseCase
import com.meetfootball.user.application.port.out.FindUserByIdDbPort

@UseCase
class FindUserByIdService(
    private val findUserByIdDbPort: FindUserByIdDbPort
): FindUserByIdUseCase {
    override fun findUserById(id: Long): ResultEntity<User> {
        val user = this.findUserByIdDbPort.findUserById(id)

        return if (user != null) ResultEntity.Success(user) else ResultEntity.Fail(ErrorEntity.NotFound)
    }
}
