package com.justforfun.bottlerocket

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//ApiFactory to create stores Api
object Apifactory{
    //OkhttpClient for building http request url
    private val client = OkHttpClient().newBuilder().build()
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://sandbox.bottlerocketapps.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    val storesApi : StoresApi = retrofit().create(StoresApi::class.java)

}