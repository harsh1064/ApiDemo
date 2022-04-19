package com.example.apidemo.ModelResponse

import com.google.gson.annotations.SerializedName

class RegisterResponse (
    var error:Int,
    @SerializedName("message")
    var msg:String
)