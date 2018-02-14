package com.example.pluu.retrofitstatecallbacksample.callback

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit

fun testCall() {
    Retrofit.Builder().build().create(API::class.java)
            .testAPI()
            .enqueue(StateRetrofitCallback { apiState ->
                when (apiState) {
                    is APIState.Success -> println("Success => ${apiState.body}")
                    is APIState.NoContents -> println("No Content")
                    is APIState.Fail -> println("API Fail")
                }
            })
}

interface API {
    fun testAPI(): Call<ResponseBody>
}