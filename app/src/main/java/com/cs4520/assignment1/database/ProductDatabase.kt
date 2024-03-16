package com.cs4520.assignment1.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao?
}