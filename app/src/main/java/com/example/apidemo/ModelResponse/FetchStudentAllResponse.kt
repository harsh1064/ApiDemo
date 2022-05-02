package com.example.apidemo.ModelResponse

import com.example.apidemo.Model.User
import com.google.gson.annotations.SerializedName

class FetchStudentAllResponse (
    @SerializedName("user")
    var studentList:ArrayList<User>,
    var error:Int
    )