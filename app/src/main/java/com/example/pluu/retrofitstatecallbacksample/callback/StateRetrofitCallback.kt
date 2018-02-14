package com.example.pluu.retrofitstatecallbacksample.callback

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

/**
 * State Pattern Style of Retrofit
 * Created by pluu on 2018. 2. 14..
 */
class StateRetrofitCallback<T>(private val callback: (APIState<T>) -> Unit) : Callback<T> {

    private val TAG = StateRetrofitCallback::class.java.simpleName

    override fun onResponse(call: Call<T>, response: Response<T>) {
        when {
            response.isSuccessful && response.code() == HttpURLConnection.HTTP_NO_CONTENT -> {
                callback.invoke(APIState.NoContents())
            }
            response.isSuccessful -> {
                response.body()?.let {
                    callback.invoke(APIState.Success(it))
                } ?: callback.invoke(APIState.Fail())
            }
            else -> callback.invoke(APIState.Fail())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        callback.invoke(APIState.Fail())
    }
}

sealed class APIState<out T> {
    class Success<out T>(val body: T) : APIState<T>()
    class NoContents<out T> : APIState<T>()
    class Fail<out T> : APIState<T>()
}