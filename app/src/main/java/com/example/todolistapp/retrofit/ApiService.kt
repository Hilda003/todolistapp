package com.example.todolistapp.retrofit

import com.example.todolistapp.data.ModelQuote
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET

interface ApiService {

    @GET("quotes/random")
    fun getQuotes(): Call<List<ModelQuote>>
}

