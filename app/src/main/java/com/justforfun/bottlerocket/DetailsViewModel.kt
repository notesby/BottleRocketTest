package com.justforfun.bottlerocket

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {
    // TODO: Implement the ViewModel

    private val repository : StoresRepository = StoresRepository(Apifactory.storesApi)

    val storeLiveData = MutableLiveData<Store>()

    fun fetchStore(context: Context,storeid:Long){
        viewModelScope.launch {
            val store: Store = AppDatabase.getInstance(context).storeDao().findById(storeid)
            Log.d("DetailsViewModel",store.toString())
            storeLiveData.postValue(store)
        }
    }

}