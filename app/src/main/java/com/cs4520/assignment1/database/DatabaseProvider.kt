package com.cs4520.assignment1.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room


@SuppressLint("StaticFieldLeak")
object DatabaseProvider {

    private var instance: ProductDatabase? = null
    private var context: Context? = null;

    fun setContext(context: Context) {
        this.context = context;
    }

    fun getDatabase(): ProductDatabase? {
        if (instance == null && context != null) {
            instance = Room.databaseBuilder(
                context!!,
                ProductDatabase::class.java, "my_database"
            ).build();
        }
        return instance
    }
}