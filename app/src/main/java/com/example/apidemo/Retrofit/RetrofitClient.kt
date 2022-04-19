package com.example.apidemo.Retrofit

import com.example.apidemo.Api.ApiInterface
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    val BASE_URL = "http://192.168.43.53/api_student/"
    var retrofitClient:RetrofitClient? =null
    var retrofit:Retrofit? = null

    constructor(){
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getInstance():RetrofitClient{
        if(retrofitClient==null){
            retrofitClient = RetrofitClient()
        }
        return retrofitClient as RetrofitClient
    }

    fun getApi():ApiInterface{
        return retrofit!!.create(ApiInterface::class.java)
    }
}