package com.meetfootball.application.base

import com.meetfootball.enum.ErrorCode

sealed class Error(
    val statusCode: Int,
    val errorCode: ErrorCode,
    val message: String
) {
    data object NotFound: Error(404, ErrorCode.NOT_FOUND, "Entity Not Found")
}