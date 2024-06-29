package com.meetfootball.user.application.domain.entity

sealed class ResultEntity<T> {
    data class Success<T>(val data: T): ResultEntity<T>()

    data class Fail<T>(val error: ErrorEntity): ResultEntity<T>()
}
