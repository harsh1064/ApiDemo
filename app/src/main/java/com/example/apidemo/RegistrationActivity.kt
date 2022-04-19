package com.example.apidemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.apidemo.ModelResponse.LoginResponse
import com.example.apidemo.ModelResponse.RegisterResponse
import com.example.apidemo.Retrofit.RetrofitClient
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    lateinit var edtName : EditText
    lateinit var edtemail : EditText
    lateinit var edtPassword : EditText
    lateinit var edtConfirmPassword : EditText
    lateinit var btnSignUp1: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        edtName = findViewById(R.id.edtName)
        edtemail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword)
        btnSignUp1 = findViewById(R.id.btnSignUp1)

    }

    override fun onStart() {
        super.onStart()

        btnSignUp1.setOnClickListener{
            performRegister()
        }
    }

    private fun performRegister() {

        val userName = edtName.text.toString()
        val userEmail = edtemail.text.toString()
        val userPassword = edtPassword.text.toString()
        val userConfirmPassword = edtConfirmPassword.text.toString()

        if (userName.isEmpty()){
            edtName.requestFocus()
            edtName.error = "Please Enter Name"
            return
        }

        if (userEmail.isEmpty()){
            edtemail.requestFocus()
            edtemail.error = "Please Enter Email"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            edtemail.requestFocus()
            edtemail.error = "Please Enter Correct Email"
            return
        }
        if (userPassword.isEmpty()){
            edtPassword.requestFocus()
            edtPassword.error = "Please Enter Password"
            return
        }
        if (userPassword.length<8){
            edtPassword.requestFocus()
            edtPassword.error = "Password must have 8 Length"
            return
        }
        if (userPassword != userConfirmPassword){
            edtConfirmPassword.requestFocus()
            edtConfirmPassword.error = "Password does no match"
            return
        }
        if (userConfirmPassword.length<8){
            edtConfirmPassword.requestFocus()
            edtConfirmPassword.error = "Password does not match"
            return
        }



        var call: retrofit2.Call<RegisterResponse> = RetrofitClient()
            .getInstance()
            .getApi()
            .register(edtName.text.toString(),edtemail.text.toString(),edtPassword.text.toString())

        call.enqueue(object : retrofit2.Callback<RegisterResponse>{
            override fun onResponse(
                call: retrofit2.Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()!!.error == 0){
                        Toast.makeText(this@RegistrationActivity,response.body()!!.msg,Toast.LENGTH_LONG).show()
                    }else{
                        var intent = Intent(this@RegistrationActivity,MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegistrationActivity,t.message.toString(), Toast.LENGTH_LONG).show()
            }

        })
    }
}