package com.example.apidemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.apidemo.Adapter.StudentAdapter
import com.example.apidemo.Model.User
import com.example.apidemo.ModelResponse.FetchStudentAllResponse
import com.example.apidemo.Retrofit.RetrofitClient
import com.example.apidemo.SharedPreference.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var studentRecyclerView: RecyclerView
    lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentRecyclerView = findViewById(R.id.studentRecyclerView)

        var call:Call<FetchStudentAllResponse> = RetrofitClient()
            .getInstance()
            .getApi()
            .fetchAllStudent()

        call.enqueue(object: Callback<FetchStudentAllResponse> {
            override fun onResponse(call: Call<FetchStudentAllResponse>, response: Response<FetchStudentAllResponse>) {
                if (response.isSuccessful){
                    var studentResponse:FetchStudentAllResponse = response.body()!!

                    if (studentResponse.error == 0){
                        var studentList:ArrayList<User> = studentResponse.studentList

                        studentRecyclerView.adapter = StudentAdapter(this@MainActivity!!,studentList)
                    }else{
                        Toast.makeText(this@MainActivity,"No Data Found",Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<FetchStudentAllResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message.toString(), Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.update -> {
                var intent:Intent = Intent(this,UpdateActivity::class.java)
                startActivity(intent)
            }
            R.id.logout -> {
                sharedPreference = SharedPreference(this)
                sharedPreference.logout()
                var intent:Intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}