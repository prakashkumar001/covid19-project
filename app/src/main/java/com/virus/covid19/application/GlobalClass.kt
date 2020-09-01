package com.virus.covid19.application

import android.app.Application
import com.virus.covid19.database.entities.Product
import com.virus.covid19.database.entities.User

class GlobalClass : Application() {
    companion object
    {
        var sInstance:GlobalClass?=null
        var cartList=ArrayList<Product>()


        fun getInstance(): GlobalClass? {
            if (sInstance != null) {
                return sInstance
            } else {
                sInstance=GlobalClass()
                return sInstance
            }
        }

        fun setInstance(globalClass: GlobalClass) {
            sInstance = globalClass
        }

    }
    var userInfo=User()

    override fun onCreate() {
        super.onCreate()
        setInstance(this)
    }

}