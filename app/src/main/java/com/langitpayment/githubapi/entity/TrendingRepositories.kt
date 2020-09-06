package com.langitpayment.githubapi.entity

import com.squareup.moshi.Json

data class TrendingRepositories(
    @Json(name = "author")  val author: String,
    @Json(name = "name")  val name: String,
    @Json(name = "avatar")  val avatar: String
)