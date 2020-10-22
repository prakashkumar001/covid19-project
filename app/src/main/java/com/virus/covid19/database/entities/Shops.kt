package com.virus.covid19.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shops")

class Shops {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var shopName: String? = null
    var shopType:String?=null
    var shopImageUrl:String?=null
    var shopBgColor:String?=null
    var location:String?=null


}