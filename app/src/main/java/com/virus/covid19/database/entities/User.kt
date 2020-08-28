package com.virus.covid19.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var email: String? = null
    var mobile: String? = null
    var pincode: String? = null
    var address: String? = null
    var password: String? = null
    var isSocialLogin: Boolean? = null

}