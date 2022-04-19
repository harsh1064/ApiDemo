package com.example.apidemo.ModelResponse

import com.example.apidemo.Model.User

class LoginResponse (
    var user: User,
            var error:Int,
                    var message:String
)