package com.langitpayment.githubapi.network

import com.langitpayment.githubapi.entity.TrendingRepositories
import retrofit2.Call
import retrofit2.http.GET

interface GithubApiService {
    @GET("repositories")
    suspend fun getRepositories(): List<TrendingRepositories>
}