package com.example.apidemo

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.apidemo.ModelResponse.LoginResponse
import com.example.apidemo.Retrofit.RetrofitClient
import com.example.apidemo.SharedPreference.SharedPreference
import retrofit2.Response
import javax.security.auth.callback.Callback

class LoginActivity : AppCompatActivity() {

    lateinit var edtEmail:EditText
    lateinit var edtPassword:EditText
    lateinit var btnSignIn:AppCompatButton
    lateinit var btnSignUp:AppCompatButton

    lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnSignIn = findViewById(R.id.btnsignin)
        btnSignUp = findViewById(R.id.btnsignup)

        sharedPreference = SharedPreference(this)

        if(sharedPreference.isLoggedIn()){
            var intent = Intent(this@LoginActivity,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        btnSignIn.setOnClickListener{
          performLogin()
        }
        btnSignUp.setOnClickListener{
            openRegistrationActivity()
        }
    }

    private fun openRegistrationActivity() {
        var intent = Intent(this,RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun performLogin(){

        val userEmail = edtEmail.text.toString()
        val userPassword = edtPassword.text.toString()


        if (userEmail.isEmpty()){
            edtEmail.requestFocus()
            edtEmail.error = "Please Enter Email"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            edtEmail.requestFocus()
            edtEmail.error = "Please Enter Correct Email"
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


        val call : retrofit2.Call<LoginResponse> = RetrofitClient()
            .getInstance()
            .getApi()
            .login(edtEmail.text.toString(),edtPassword.text.toString())

        call.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: retrofit2.Call<LoginResponse>, response: Response<LoginResponse>
            ) {
                if (response.isSuccessful){
                    if (response.body()!!.error == 0) {
                        sharedPreference.saveUser(response.body()!!.user)
                        var intent = Intent(this@LoginActivity,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        Toast.makeText(this@LoginActivity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@LoginActivity,response.body()!!.message,Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

        })
    }
}