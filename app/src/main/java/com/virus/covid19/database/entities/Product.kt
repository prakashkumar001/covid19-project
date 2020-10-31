package com.virus.covid19.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
class Product constructor() {
    constructor(
        product_name: String?,
        price: String?,
        qty: String?,
        product_desc: String?,
        product_type: String?,
        product_shop: String?
    ):this() {
        this.product_name = product_name
        this.price = price
        this.qty = qty
        this.product_desc = product_desc
        this.product_type = product_type
        this.product_shop = product_shop

    }


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var shop_id: String? = null
    var product_name: String? = null
    var price: String? = null
    var qty: String? = null
    var product_desc: String? = null
    var product_type: String? = null
    var product_image: String? = null
    var product_shop: String? = null



}