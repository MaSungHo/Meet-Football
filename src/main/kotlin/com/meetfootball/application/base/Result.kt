package com.meetfootball.application.base

sealed class Result<T> {
    data class Success<T>(val data: T): Result<T>()

    data class Fail<T>(val error: Error): Result<T>()
}
