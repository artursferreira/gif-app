package com.artur.giphyapp.base

import retrofit2.Response
import com.artur.giphyapp.data.remote.Result

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            }
            return Result.Error(Exception(response.message()))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

}