package com.app2641.moonlovers.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://moon-lovers.herokuapp.com"
private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val client = OkHttpClient.Builder().addInterceptor(interceptor)
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client.build())
        .baseUrl(BASE_URL)
        .build()

interface MoonAgeApiService {
    @GET("/")
    suspend fun getMoonAge(): MoonAgeProperty
}

object MoonAgeApi {
    val retrofitService: MoonAgeApiService by lazy {
        retrofit.create(MoonAgeApiService::class.java)
    }
}