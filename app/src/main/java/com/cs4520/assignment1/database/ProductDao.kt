package com.cs4520.assignment1.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    suspend fun getAll(): List<ProductEntity?>?

    @Insert
    suspend fun insertAll(product: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clearProducts()
}