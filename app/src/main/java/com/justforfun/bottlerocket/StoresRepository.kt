package com.justforfun.bottlerocket

class StoresRepository(private val api : StoresApi) : BaseRepository() {

    suspend fun getStores() : MutableList<Store>?{

        val storesResponse = safeApiCall(
            call = {api.getStores()},
            errorMessage = "Error Fetching Stores"
        )

        return storesResponse?.stores!!.toMutableList()

    }

}