package com.virus.covid19.interfaces

import com.virus.covid19.database.entities.Shops

interface CardClickListener {
    fun onCardClick(shopName: String)
   abstract fun getCategoryName(category:String)
}