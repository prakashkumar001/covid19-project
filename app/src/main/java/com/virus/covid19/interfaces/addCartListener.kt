package com.virus.covid19.interfaces

import com.virus.covid19.database.entities.Product

interface addCartListener {
    fun addToCart(product: Product)

}