package com.virus.covid19.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_history")
class OrderHistory {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var shop_id: String? = null
    var userId: Int? = null
    var shop_name: String? = null
    var totalAmount: String? = null
    var totalProducts: String? = null
    var status: String? = null

    constructor(
        shop_id: String?,
        userId:Int?,
        shop_name: String?,
        totalAmount: String?,
        totalProducts: String?,
        status:String?
    ) {
        this.shop_id = shop_id
        this.userId = userId
        this.shop_name = shop_name
        this.totalAmount = totalAmount
        this.totalProducts = totalProducts
        this.status = status

    }



}