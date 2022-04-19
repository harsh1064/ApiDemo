package com.example.apidemo.Api

import com.example.apidemo.ModelResponse.LoginResponse
import com.example.apidemo.ModelResponse.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("stud_name") name:String,
        @Field("stud_email") email:String,
        @Field("pwd") password:String
    ):Call<RegisterResponse>


    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("stud_email") email:String,
        @Field("pwd") password:String
    ):Call<LoginResponse>
}