package com.justforfun.bottlerocket

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stores")
data class Store(
    @PrimaryKey val storeID: Long,
    @ColumnInfo val zipcode: Int,
    @ColumnInfo val latitude: Double,
    @ColumnInfo val longitude: Double,
    @ColumnInfo val address: String,
    @ColumnInfo val city: String,
    @ColumnInfo val state: String,
    @ColumnInfo val name: String,
    @ColumnInfo val storeLogoURL: String,
    @ColumnInfo val phone: String
)