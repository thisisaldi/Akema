package com.kari.akema.services

import com.kari.akema.models.auth.LoginRequest
import com.kari.akema.models.auth.LoginResponse
import com.kari.akema.models.StudentDataResponse
import com.kari.akema.models.auth.LogoutResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("/logout")
    fun logout(): Call<LogoutResponse>

    @GET("/student")
    suspend fun getStudent(): Response<StudentDataResponse>
}