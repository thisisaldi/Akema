package com.kari.akema.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.kari.akema.R
import com.kari.akema.models.Student
import com.kari.akema.models.StudentDataResponse


class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val TOKEN = "token"
        const val STUDENT_NAME = "name"
        const val STUDENT_NIM = "nim"
        const val STUDENT_EMAIL = "email"
        const val STUDENT_ADDRESS = "address"
        const val STUDENT_CLASS = "classOf"
    }

    fun setStudentDetails(studentData: StudentDataResponse) {
        val editor = prefs.edit()
        val data: Student = studentData.data
        Log.d("student_data_view", studentData.toString())
        editor.putString(STUDENT_NAME, data.name)
        editor.putString(STUDENT_NIM, data.nim)
        editor.putString(STUDENT_EMAIL, data.email)
        editor.putString(STUDENT_ADDRESS, data.address)
        editor.putString(STUDENT_CLASS, data.classOf.toString())

        editor.apply()

    }

    fun getStudentDetails(): HashMap<String, String> {
        val student = HashMap<String, String>()

        student["name"] = prefs.getString(STUDENT_NAME, null).toString()
        student["nim"] = prefs.getString(STUDENT_NIM, null).toString()
        student["email"] = prefs.getString(STUDENT_EMAIL, null).toString()
        student["address"] = prefs.getString(STUDENT_ADDRESS, null).toString()
        student["classOf"] = prefs.getString(STUDENT_CLASS, null).toString()

        return student
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun deleteAuthToken() {
        val editor = prefs.edit()
        editor.remove(TOKEN);
        editor.apply();
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(TOKEN, null)
    }
}