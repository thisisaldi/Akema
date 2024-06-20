package com.kari.akema.services

import com.kari.akema.models.LoginRequest
import com.kari.akema.models.LoginResponse
import com.kari.akema.models.StudentDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("/students")
    fun getStudents(): Call<StudentDataResponse>
}