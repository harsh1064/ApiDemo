package com.example.apidemo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.apidemo.Model.User
import com.example.apidemo.ModelResponse.LoginResponse
import com.example.apidemo.R
import com.example.apidemo.Retrofit.RetrofitClient
import com.example.apidemo.SharedPreference.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentAdapter(var context: Context,var studentList:ArrayList<User>) : RecyclerView.Adapter<StudentAdapter.studentViewHolder>() {

    class studentViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var txtUserName:TextView = itemView.findViewById(R.id.txtStudentName)
        var txtUserEmail:TextView = itemView.findViewById(R.id.txtStudentEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): studentViewHolder {
        var view : View = LayoutInflater.from(context).inflate(R.layout.student_item,parent,false)
        return studentViewHolder(view)
    }

    override fun onBindViewHolder(holder: studentViewHolder, position: Int) {
        holder.txtUserName.text = studentList[position].stud_name
        holder.txtUserEmail.text = studentList[position].stud_email
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

}