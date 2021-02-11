package com.artur.giphyapp.extensions

import com.artur.giphyapp.data.remote.NetworkResult
import com.artur.giphyapp.data.remote.exception.AppException

suspend fun <T> handleException(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.success(apiCall.invoke())
    } catch (throwable: Throwable) {
        NetworkResult.error(AppException(throwable))
    }
}