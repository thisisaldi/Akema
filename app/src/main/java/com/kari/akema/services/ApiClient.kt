package com.kari.akema.services

import android.content.Context
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

    private fun generateSecureOkHttpClient(context: Context): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        val certificateFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
        val caInput: InputStream = context.resources.openRawResource(R.raw.localhost)
        val ca: X509Certificate =
            caInput.use { certificateFactory.generateCertificate(it) as X509Certificate }

        val keyStoreType = KeyStore.getDefaultType()
        val keyStore: KeyStore = KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
            setCertificateEntry("ca", ca)
        }

        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        require(trustManagers.size == 1 && trustManagers[0] is X509TrustManager) {
            "Unexpected default trust managers: ${trustManagers.contentToString()}"
        }
        val trustManager = trustManagers[0] as X509TrustManager

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(trustManager), SecureRandom())

        val hostnameVerifier = HostnameVerifier { _, _ -> true }

        return httpClientBuilder
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier(hostnameVerifier) // Disable hostname verification
            .build()
    }


    fun getApiService(): ApiService {
        // Initialize ApiService if not initialized yet
        if (!::apiService.isInitialized) {
            val httpClientBuilder = generateSecureOkHttpClient(context).newBuilder()
            httpClientBuilder.addInterceptor(AddCookiesInterceptor(context))

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.DEV_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }fun getApiService(context: Context): ApiService {
        if (!::apiService.isInitialized) {
            val httpClientBuilder = generateSecureOkHttpClient(context).newBuilder()
            httpClientBuilder.addInterceptor(AddCookiesInterceptor(context))

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.DEV_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }
}
