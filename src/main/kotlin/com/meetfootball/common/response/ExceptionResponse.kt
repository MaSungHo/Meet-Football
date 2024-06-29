package com.meetfootball.common.response

import com.meetfootball.user.application.domain.entity.ResultEntity

data class ExceptionResponse<T>(
    private val resultEntity: ResultEntity.Fail<T>,
    val message: String = resultEntity.error.message,
    val errorCode: String = resultEntity.error.errorCode.code,
) : ApiResponse<T>(resultEntity)
