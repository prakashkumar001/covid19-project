package com.virus.covid19.database.dao

import androidx.room.*
import com.virus.covid19.database.entities.OrderHistory

@Dao
interface OrderHistoryDao {
    @Query("SELECT * FROM order_history WHERE userId= :userId ORDER BY ID")
    fun loadAllOrder(userId:Long): List<OrderHistory>?

    @Insert
    fun insertOrder(orderHistory: OrderHistory?)

    @Update
    fun updateOrder(orderHistory: OrderHistory?)

    @Delete
    fun delete(orderHistory: OrderHistory?)

    @Query("SELECT * FROM order_history WHERE id = :id")
    fun loadOrderById(id: Int): OrderHistory?
}