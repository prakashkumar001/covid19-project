package com.virus.covid19.database.dao

import androidx.room.*
import com.virus.covid19.database.entities.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY ID")
    fun loadAllProduct(): List<Product>?

    @Query("SELECT * FROM products WHERE product_type = :productType ORDER BY ID")
    fun loadAllByProduct(productType:String): List<Product>?

    @Insert
    fun insertProduct(user: Product?)

    @Update
    fun updateProduct(user: Product?)

    @Delete
    fun delete(user: Product?)

    @Query("SELECT * FROM products WHERE id = :id")
    fun loadProductById(id: Int): Product?
}

