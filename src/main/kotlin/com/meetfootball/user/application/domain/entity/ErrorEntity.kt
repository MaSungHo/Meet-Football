package com.meetfootball.user.application.domain.entity

import com.meetfootball.common.enums.ErrorCode

sealed class ErrorEntity(
    val statusCode: Int,
    val errorCode: ErrorCode,
    val message: String
) {
    data object NotFound: ErrorEntity(404, ErrorCode.NOT_FOUND, "User Not Found")
}