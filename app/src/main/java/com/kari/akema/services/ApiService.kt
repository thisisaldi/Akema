package com.kari.akema.services

import com.kari.akema.models.presence.PresentRequest
import com.kari.akema.models.presence.PresentResponse
import com.kari.akema.models.auth.LoginRequest
import com.kari.akema.models.auth.LoginResponse
import com.kari.akema.models.student.StudentDataResponse
import com.kari.akema.models.auth.LogoutResponse
import com.kari.akema.models.presence.AttendanceRequest
import com.kari.akema.models.presence.AttendanceResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("/logout")
    fun logout(): Call<LogoutResponse>

    @GET("/student")
    suspend fun getStudent(): Response<StudentDataResponse>

    @POST("/present")
    suspend fun present(
        @Body request: PresentRequest
    ): PresentResponse

    @HTTP(method = "GET", path = "/attendance", hasBody = true)
    fun getAttendance(@Body request: AttendanceRequest): Response<AttendanceResponse>
}