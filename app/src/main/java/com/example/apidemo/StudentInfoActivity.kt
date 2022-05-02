package com.example.apidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.apidemo.SharedPreference.SharedPreference

class StudentInfoActivity : AppCompatActivity() {

    lateinit var txtStudentName: TextView
    lateinit var txtStudentEmail: TextView
    lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_info)

        txtStudentName = findViewById(R.id.txtStudentNameDashboard)
        txtStudentEmail = findViewById(R.id.txtStudentEmailDashboard)

        sharedPreference = SharedPreference(this)

        txtStudentName.text = "hy,"+sharedPreference.getUser().stud_name
        txtStudentEmail.text = sharedPreference.getUser().stud_email

    }
}