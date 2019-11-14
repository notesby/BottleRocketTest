package com.justforfun.bottlerocket

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class StoreListViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val repository : StoresRepository = StoresRepository(Apifactory.storesApi)

    val storesLiveData = MutableLiveData<MutableList<Store>>()

    fun fetchStores(context:Context, manualRefresh: Boolean){
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val inCache = pref.getBoolean("inCache", false)
        viewModelScope.launch {
            var stores : MutableList<Store>?
            if (!inCache) {
                stores = repository.getStores()
                AppDatabase.getInstance(context).storeDao().insertAll(stores!!)
                pref.edit().putBoolean("inCache",true).apply()
                Log.d("ViewModel"," network")
            }
            else if (inCache && manualRefresh)
            {
                stores = repository.getStores()
                AppDatabase.getInstance(context).storeDao().insertAll(stores!!)
                pref.edit().putBoolean("inCache",true).apply()
                Log.d("ViewModel","manual refresh")
            }
            else
            {
                stores = AppDatabase.getInstance(context).storeDao().getAll()
                Log.d("ViewModel","from cache")
            }
            storesLiveData.postValue(stores)
        }
    }

}
