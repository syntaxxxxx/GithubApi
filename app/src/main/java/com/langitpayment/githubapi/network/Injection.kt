package com.langitpayment.githubapi.network

import com.langitpayment.githubapi.BuildConfig
import com.langitpayment.githubapi.entity.TrendingRepositories
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

object Injection {

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(provideLoggingInterceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
        return okHttpClient
    }

    fun provideRetrofit(url: String = "https://ghapi.huchen.dev/"): Retrofit {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(provideOkHttpClient())
            .build()
    }

    fun provideGithubApiService(): GithubApiService = provideRetrofit().create(GithubApiService::class.java)
}