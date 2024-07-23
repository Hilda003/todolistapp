package com.example.todolistapp.retrofit

import android.content.Context
import android.util.Log
import android.view.Display.Mode
import android.widget.Toast
import com.example.todolistapp.data.ModelQuote
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    fun getQuotes(context: Context, callback: (List<ModelQuote>) -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.quotable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: ApiService = retrofit.create(ApiService::class.java)

        val call: Call<List<ModelQuote>> = service.getQuotes()
        call.enqueue(object : Callback<List<ModelQuote>> {
            override fun onResponse(p0: Call<List<ModelQuote>>, p1: Response<List<ModelQuote>>) {
                if (p1.isSuccessful) {
                    val quotes: List<ModelQuote> = p1.body() ?: emptyList()
                    callback(quotes)
                } else {
                    Log.e("ApiCall", "Response not successful: ${p1.code()}")
                }
            }

            override fun onFailure(p0: Call<List<ModelQuote>>, p1: Throwable) {
                Log.e("ApiCall", "Request failed", p1)
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


