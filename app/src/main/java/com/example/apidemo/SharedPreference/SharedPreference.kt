package com.example.apidemo.SharedPreference

import android.content.Context
import android.content.SharedPreferences
import com.example.apidemo.Model.User

class SharedPreference(val context: Context) {
    val SHARED_PREF_NAME = "StudentPhpApi"
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    fun saveUser(user: User) {
        sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        editor.putInt("id", user.stud_id)
        editor.putString("name", user.stud_name)
        editor.putString("email", user.stud_email)
        editor.putBoolean("logged", true)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean("logged", false)
    }

    fun getUser(): User {
        sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return User(
            sharedPreference.getInt("id", -1),
            sharedPreference.getString("name", null)!!,
            sharedPreference.getString("email", null)!!
        )
    }

    fun logout(){
        sharedPreference = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
    }
}