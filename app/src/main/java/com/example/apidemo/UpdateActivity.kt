package com.example.apidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.apidemo.ModelResponse.LoginResponse
import com.example.apidemo.Retrofit.RetrofitClient
import com.example.apidemo.SharedPreference.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UpdateActivity : AppCompatActivity() {

    lateinit var edtusername:EditText
    lateinit var edtuseremail:EditText
    lateinit var btnupdate:Button
    lateinit var  sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        edtusername = findViewById(R.id.edtUserNameProfile)
        edtuseremail = findViewById(R.id.edtUserEmailProfile)
        btnupdate = findViewById(R.id.btnUpdate)

        sharedPreference = SharedPreference(this)

        var studId:Int = sharedPreference.getUser().stud_id.toString().toInt()

        btnupdate.setOnClickListener {
            updateuserInfo(studId)
        }

    }

    private fun updateuserInfo(studId: Int) {
        val userEmail = edtuseremail.text.toString()
        val userName = edtusername.text.toString()
        if (userEmail.isEmpty()) {
            edtuseremail.requestFocus()
            edtuseremail.error = "Please Enter Email"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            edtuseremail.requestFocus()
            edtuseremail.error = "Please Enter Correct Email"
            return
        }
        if (userName.isEmpty()) {
            edtusername.requestFocus()
            edtusername.error = "Please Enter Email"
            return
        }

        var call:Call<LoginResponse> = RetrofitClient()
            .getInstance()
            .getApi()
            .update(studId,userName,userEmail)

        call.enqueue(object :Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                var updateResponse:LoginResponse = response.body()!!

                if (response.isSuccessful){
                    if (updateResponse.error == 0){
                        sharedPreference.saveUser(updateResponse.user)
                        Toast.makeText(this@UpdateActivity,updateResponse.message,Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@UpdateActivity,updateResponse.message,Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@UpdateActivity,t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}