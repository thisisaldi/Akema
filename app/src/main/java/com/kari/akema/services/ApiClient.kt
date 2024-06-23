package com.kari.akema.services

import android.content.Context
import android.util.Log
import com.kari.akema.Constants
import com.kari.akema.R
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class ApiClient(private val context: Context) {
    private lateinit var apiService: ApiService

    private fun generateInsecureOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, SecureRandom())

        val hostnameVerifier = HostnameVerifier { _, _ -> true }

        return httpClientBuilder
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(hostnameVerifier)
            .build()
    }

    fun getApiService(): ApiService {
        if (!::apiService.isInitialized) {
            val httpClientBuilder = generateInsecureOkHttpClient().newBuilder()
            httpClientBuilder.addInterceptor(AddCookiesInterceptor(context))

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.PROD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }
}
