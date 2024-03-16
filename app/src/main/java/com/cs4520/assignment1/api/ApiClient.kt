package com.cs4520.assignment1.api

import com.cs4520.assignment1.Product
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("prod")
    fun fetchData(): Call<JsonElement>
}