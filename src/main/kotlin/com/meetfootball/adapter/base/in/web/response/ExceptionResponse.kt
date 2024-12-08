package com.meetfootball.adapter.base.`in`.web.response

import com.meetfootball.application.base.Result

data class ExceptionResponse<T>(
    private val result: Result.Fail<T>,
    val message: String = result.error.message,
    val errorCode: String = result.error.errorCode.code,
): ApiResponse()
