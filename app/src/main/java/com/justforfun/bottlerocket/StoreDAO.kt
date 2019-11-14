package com.justforfun.bottlerocket

import androidx.room.*

@Dao
interface StoreDAO {
    @Query("SELECT * FROM stores")
    suspend fun getAll(): MutableList<Store>

    @Query("SELECT * FROM stores WHERE storeID = :storeID")
    suspend fun findById(storeID: Long): Store

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stores: MutableList<Store>)

}