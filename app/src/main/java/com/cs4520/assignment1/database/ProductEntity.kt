package com.cs4520.assignment1.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
class ProductEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var name: String? = null;
    var type: String? = null;
    var price: Double? = null;
    var expiryDate: String? = null;
}