package com.artur.giphyapp.data.remote

import com.artur.giphyapp.data.remote.exception.AppException

class NetworkResult<out T> private constructor(val status: Status, val data: T?, private val exception: AppException?) {

    enum class Status {
        SUCCESS, ERROR, LOADING, CANCEL
    }

    companion object {
        fun <T> success(data: T?): NetworkResult<T> {
            return NetworkResult(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(exception: AppException? = null): NetworkResult<T> {
            return NetworkResult(
                Status.ERROR,
                null,
                exception
            )
        }

        fun <T> loading(data: T? = null): NetworkResult<T> {
            return NetworkResult(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> cancel(data: T? = null, exception: AppException? = null): NetworkResult<T> {
            return NetworkResult(
                Status.CANCEL,
                data,
                exception
            )
        }
    }

    fun getErrorMessage() = exception?.getErrorMessage()
}