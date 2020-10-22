package com.virus.covid19.model

class ShopDetail {
    constructor(product_name: String?, product_image: String?,location:String?) {
        this.product_name = product_name
        this.product_image = product_image
        this.location = location

    }

    var product_name: String? = null
    var product_image: String? = null
    var location: String? = null

}