package com.kari.akema.services

import android.content.Context
import android.util.Log
import android.webkit.CookieManager
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SessionManager(context).fetchAuthToken().toString()
        val builder = chain.request().newBuilder()
        Log.d("token", token)
        if (!token.isNullOrEmpty()) {
            builder.addHeader("Cookie", "token=$token")
        }

        return chain.proceed(builder.build())
    }
}