package com.meetfootball.common.response

import com.meetfootball.user.application.domain.entity.ResultEntity

open class ApiResponse<T>(private val result: ResultEntity<T>)