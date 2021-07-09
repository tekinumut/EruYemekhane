package com.tekinumut.eruyemekhane.di

import android.os.Build
import com.tekinumut.eruyemekhane.BuildConfig
import com.tekinumut.eruyemekhane.data.remote.MainApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    @Singleton
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApiService {
        return retrofit.create(MainApiService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val loggingBody = HttpLoggingInterceptor()
        loggingBody.setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientBuilder = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            addInterceptor(getHeaders())
            if (BuildConfig.DEBUG)
            addInterceptor(loggingBody)
            // for API 23 and above -> we'll use network_security_config.xml
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M)
                letUnsafeConnection()
        }
        return clientBuilder.build()
    }

    private fun getHeaders() = Interceptor { chain ->
        val newRequest = chain.request().newBuilder().apply {
            header("Content-Type", "text/plain;charset=UTF-8")
        }
        chain.proceed(newRequest.build())
    }

    /**
     * we trust every certificate to get rid of SSlHandleException
     * For greater than Api 23 we trust only erciyes_car certificate file
     * We initialize this certificate in network_security_config file
     * Note: I didn't find to way trust only erciyes_car certificate for below Api 23
     * and i didn't want to spend too much time to find solution
     *
     */
    private fun OkHttpClient.Builder.letUnsafeConnection(): OkHttpClient.Builder =
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) = Unit

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) = Unit

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )
            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            this.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            this.hostnameVerifier { _, _ -> true }
            this
        } catch (e: Exception) {
            this
        }
}