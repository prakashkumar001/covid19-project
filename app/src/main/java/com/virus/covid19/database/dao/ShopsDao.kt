package com.virus.covid19.database.dao

import androidx.room.*
import com.virus.covid19.database.entities.Shops
import com.virus.covid19.database.entities.User

@Dao
interface ShopsDao {
    @Query("SELECT * FROM shops ORDER BY ID")
    fun loadAllShop(): List<Shops>?

    @Query("SELECT * FROM shops WHERE shopType = :shopname ORDER BY ID")
    fun loadAllByShop(shopname:String): List<Shops>?

    @Insert
    fun insertShop(user: Shops?)

    @Update
    fun updateShop(user: Shops?)

    @Delete
    fun delete(user: Shops?)

    @Query("SELECT * FROM shops WHERE id = :id")
    fun loadShopById(id: Int): Shops?


}