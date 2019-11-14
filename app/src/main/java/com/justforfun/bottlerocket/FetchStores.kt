package com.justforfun.bottlerocket

import retrofit2.Response
import retrofit2.http.GET


// Data Model for store item
/*data class Store(
    val storeID: Long,
    val zipcode: Int,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val city: String,
    val state: String,
    val name: String,
    val storeLogoURL: String,
    val phone: String

)*/

// Data Model for the Response returned from the Api
data class StoresResponse(
    val stores: List<Store>
)

//A retrofit Network Interface for the Api
interface StoresApi{
    @GET("BR_Android_CodingExam_2015_Server/stores.json")
    suspend fun getStores(): Response<StoresResponse>
}
