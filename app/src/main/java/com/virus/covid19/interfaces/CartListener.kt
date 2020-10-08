package com.virus.covid19.interfaces

import com.virus.covid19.database.entities.Product

interface CartListener {
    fun addQtyToCart(product: Product,qty:Int)
    fun removeQtyFromCart(product: Product,qty:Int)
    fun removeFromCart(product: Product)

}