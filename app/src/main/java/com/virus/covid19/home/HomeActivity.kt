package com.virus.covid19.home

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.virus.covid19.R
import com.virus.covid19.application.GlobalClass
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.database.entities.Product
import com.virus.covid19.database.entities.Shops
import com.virus.covid19.fragments.Home
import com.virus.covid19.model.DrawerItem
import com.virus.covid19.model.ShopDetail
import com.virus.covid19.payment.CartPage
import com.virus.covid19.textview.CustomTextView
import com.virus.covid19.viewholder.DrawerAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity() {
    var navigationList:ArrayList<DrawerItem> = ArrayList()
    var backPressedCount = 0
    var cartcount:CustomTextView?=null
     var drawer_layout:DrawerLayout?=null
    private val sharedPrefFile = "Login"
    var sharedPreferences: SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        cartcount = findViewById(R.id.cartcount) as CustomTextView
        drawer_layout= findViewById(R.id.drawer_layout) as DrawerLayout

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout!!.setDrawerListener(toggle)
        toggle.syncState()

        getNavigationList()
        drawer.layoutManager=LinearLayoutManager(HomeActivity@this)
        drawer.adapter=DrawerAdapter(HomeActivity@this,navigationList)
        setProfile()
        AppExecutors.getInstance().diskIO().execute(Runnable {
            var shops=AppDatabase.getInstance(this).shopsDao().loadAllShop()
            if(shops?.size==0)
            {
                addDataIntoDB()

            }
        })
        selectFirstItemAsDefault()
        cartRelativeLayout.setOnClickListener(View.OnClickListener {
            var intent= Intent(HomeActivity@this,CartPage::class.java)
            startActivity(intent)
        })

    }

    private fun selectFirstItemAsDefault() {
        val home = Home()
        val bundle = Bundle()
        home.setArguments(bundle)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, home, home.tag).commit()
    }

    private fun getNavigationList()
    {
        navigationList.add(DrawerItem("Home",R.drawable.cart_icon))
        navigationList.add(DrawerItem("My Profile",R.drawable.cart_icon))
        navigationList.add(DrawerItem("My Orders",R.drawable.cart_icon))
        navigationList.add(DrawerItem("Log Out",R.drawable.cart_icon))



    }

    private fun addDataIntoDB()
    {
        AppExecutors.getInstance().diskIO().execute(Runnable {
            var grocery=ArrayList<ShopDetail>()
            grocery.add(ShopDetail("Nilgris","https://th.thgim.com/migration_catalog/article10983021.ece/ALTERNATES/FREE_660/Nil","all"))
            grocery.add(ShopDetail("Global","https://media-exp1.licdn.com/dms/image/C4D0BAQGBO2-ay1Jz0w/company-logo_200_200/0?e=2159024400&v=beta&t=4bi8yf7qmVvLbdxLY9GTSIy2yt_P_TmAfoa6NeNUi3Y","all"))
            grocery.add(ShopDetail("Nature Stores","https://cdn-a.william-reed.com/var/wrbm_gb_food_pharma/storage/images/7/6/4/6/2576467-1-eng-GB/US-market-can-support-at-least-1-100-Natural-Grocers-by-Vitamin-Cottage-stores-says-IPO-filing_wrbm_large.jpg","all"))
            grocery.add(ShopDetail("Snack Paradise","https://media.istockphoto.com/photos/group-of-sweet-and-salty-snacks-perfect-for-binge-watching-picture-id1149135424","all"))
            grocery.add(ShopDetail("Reliance Super Market","https://content.jdmagicbox.com/comp/vadodara/k6/0265px265.x265.140402182816.y1k6/catalogue/reliance-market-undera-vadodara-grocery-wholesalers-1dilhbi.jpg","all"))

            /*Trichy*/
            grocery.add(ShopDetail("Reliance Digital Mart","","Trichy"))
            grocery.add(ShopDetail("Roshan Maligai","","Trichy"))
            grocery.add(ShopDetail("Padma Mart","","Trichy"))
            grocery.add(ShopDetail("Saraswathi Stores","","Trichy"))
            grocery.add(ShopDetail("Kaveri Super Market","","Trichy"))


            /*Kumbakonam*/
            grocery.add(ShopDetail("City Super Maligai","","Kumbakonam"))
            grocery.add(ShopDetail("Alangar Maligai","","Kumbakonam"))
            grocery.add(ShopDetail("Greenz Shopping Mart","","Kumbakonam"))
            grocery.add(ShopDetail("Sangam Maligai","","Kumbakonam"))
            grocery.add(ShopDetail("Patanjali Arogya Kendra","","Kumbakonam"))

            for(i in 0 until grocery.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=grocery.get(i).product_name
                shop.shopType="Grocery Shops"
                shop.shopBgColor="#FFFFFF"
                shop.shopImageUrl=grocery.get(i).product_image
                shop.location=grocery.get(i).location

                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }


            var saloon=ArrayList<ShopDetail>()
            saloon.add(ShopDetail("Tony & Guy","","all"))
            saloon.add(ShopDetail("Naturals","","all"))
            saloon.add(ShopDetail("Green Trends","","all"))
            saloon.add(ShopDetail("City Saloon","","all"))
            saloon.add(ShopDetail("Scissors & Razors","","all"))
            saloon.add(ShopDetail("Beauty Hair Saloon","","all"))

            /*Kumbakonam*/
            saloon.add(ShopDetail("Bala Saloon Shop","","Kumbakonam"))
            saloon.add(ShopDetail("Mayuio Ladies Beauty Parlour","","Kumbakonam"))
            saloon.add(ShopDetail("Sri Saloon For Men","","Kumbakonam"))
            saloon.add(ShopDetail("Kalai Beauty Parlour","","Kumbakonam"))
            saloon.add(ShopDetail("Priya Beauty Herbal Plus","","Kumbakonam"))

            /*Trichy*/
            saloon.add(ShopDetail("Zazzle Men Saloon","","Trichy"))
            saloon.add(ShopDetail("Golden Green Saloon","","Trichy"))
            saloon.add(ShopDetail("ADM Saloon","","Trichy"))
            saloon.add(ShopDetail("Gold Stone Saloon","","Trichy"))
            saloon.add(ShopDetail("MuthuKumar Saloon","","Trichy"))



            for(i in 0 until saloon.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=saloon.get(i).product_name
                shop.shopType="Saloon"
                shop.shopBgColor="#FFFFFF"
                shop.shopImageUrl=saloon.get(i).product_image
                shop.location=saloon.get(i).location

                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }




            var medicals=ArrayList<ShopDetail>()
            medicals.add(ShopDetail("Apollo Pharmacy","","all"))
            medicals.add(ShopDetail("Public Pharmacy","","all"))
            medicals.add(ShopDetail("Vista Pharmacy","","all"))
            medicals.add(ShopDetail("Stanley's Pharmacy","","all"))
            medicals.add(ShopDetail("Joseph Medicals","","all"))
            medicals.add(ShopDetail("KMC Medicals","","all"))

            /*Trichy*/
            medicals.add(ShopDetail("Apollo Medicals","","Trichy"))
            medicals.add(ShopDetail("Lakshmi Medicals","","Trichy"))
            medicals.add(ShopDetail("Trichy Homeo Medicals","","Trichy"))
            medicals.add(ShopDetail("MedPlus Medicals","","Trichy"))
            medicals.add(ShopDetail("AVC Medicals & Generals","","Trichy"))

            /*Kumbakonam*/
            medicals.add(ShopDetail("Kurunji Medicals","","Kumbakonam"))
            medicals.add(ShopDetail("Bawa General Medicines","","Kumbakonam"))
            medicals.add(ShopDetail("Amman Medicals","","Kumbakonam"))
            medicals.add(ShopDetail("Cauvery MediPlus","","Kumbakonam"))
            medicals.add(ShopDetail("Annai Siddha Medicals","","Kumbakonam"))


            for(i in 0 until medicals.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=medicals.get(i).product_name
                shop.shopType="Medicals"
                shop.shopBgColor="#FFFFFF"
                shop.shopImageUrl=medicals.get(i).product_image
                shop.location=medicals.get(i).location

                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }




            var hotel=ArrayList<ShopDetail>()
            hotel.add(ShopDetail("Saravana Bhavan","","all"))
            hotel.add(ShopDetail("Sea Shell","","all"))
            hotel.add(ShopDetail("Thalapakatti","","all"))
            hotel.add(ShopDetail("Kannapa","","all"))
            hotel.add(ShopDetail("Banna Leaf","","all"))
            hotel.add(ShopDetail("KFC","","all"))
            hotel.add(ShopDetail("Dominos","","all"))

            /*Kumbakonam*/
            hotel.add(ShopDetail("Satharas Restaurent","","Kumbakonam"))
            hotel.add(ShopDetail("Karakudi Chettinadu Restaurent","","Kumbakonam"))
            hotel.add(ShopDetail("Sharma Hotel (Veg & Non-veg)","","Kumbakonam"))
            hotel.add(ShopDetail("Alif Tandoori Restaurent","","Kumbakonam"))
            hotel.add(ShopDetail("Murali Cafe","","Kumbakonam"))
            hotel.add(ShopDetail("Masala Cafe","","Kumbakonam"))

            /*Trichy*/
            hotel.add(ShopDetail("Kannapa Hotel","","Trichy"))
            hotel.add(ShopDetail("Saraswathi Cafe (Veg)","","Trichy"))
            hotel.add(ShopDetail("Sangeetha Restaurent","","Trichy"))
            hotel.add(ShopDetail("Ezham Suvai Hotel","","Trichy"))
            hotel.add(ShopDetail("Murugan Idli Shop","","Trichy"))


            for(i in 0 until hotel.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=hotel.get(i).product_name
                shop.shopType="Hotels"
                shop.shopBgColor="#FFFFFF"
                shop.shopImageUrl=hotel.get(i).product_image
                shop.location=hotel.get(i).location

                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }


            var physio=ArrayList<ShopDetail>()
            physio.add(ShopDetail("SRM Hospital","","all"))
            physio.add(ShopDetail("SRV Physio","","all"))
            physio.add(ShopDetail("Physiotheraphy and card","","all"))

            /*Trichy*/
            physio.add(ShopDetail("Jeevam Physiotheraphy","","Trichy"))
            physio.add(ShopDetail("Kerala Kottakal Physiotheraphy","","Trichy"))
            physio.add(ShopDetail("CARE Physiotheraphist Center","","Trichy"))
            physio.add(ShopDetail("Mary Madha Physiotheraphy","","Trichy"))
            physio.add(ShopDetail("Ramya Clinic","","Trichy"))

            /*Kumbakonam*/
            physio.add(ShopDetail("Physio-Care Clinic","","Kumbakonam"))
            physio.add(ShopDetail("Sri Vinayaga Physio Clinic","","Kumbakonam"))
            physio.add(ShopDetail("Dr.ArulSelvi Clinic","","Kumbakonam"))
            physio.add(ShopDetail("Q-Spine Physio Clinic","","Kumbakonam"))
            physio.add(ShopDetail("Swathi Lab & Physiotheraphy","","Kumbakonam"))


            for(i in 0 until physio.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=physio.get(i).product_name
                shop.shopType="Physiotherapist"
                shop.shopBgColor="#FFFFFF"
                shop.shopImageUrl=physio.get(i).product_image
                shop.location=physio.get(i).location

                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }


            var vegetables=ArrayList<ShopDetail>()
            vegetables.add(ShopDetail("Garden Roots","","all"))
            vegetables.add(ShopDetail("SRR Vegetable Market","","all"))
            vegetables.add(ShopDetail("At your door Market","","all"))
            vegetables.add(ShopDetail("Muruyan Vegetable Shop","","all"))
            vegetables.add(ShopDetail("Diamond Vegetable Market","","all"))
            vegetables.add(ShopDetail("AVC Vegetable Market","","all"))

          /*Trichy*/
            vegetables.add(ShopDetail("Padma Pazhamudir","","Trichy"))
            vegetables.add(ShopDetail("Kumar Mandi","","Trichy"))
            vegetables.add(ShopDetail("Srinivasa Vegetable Shop","","Trichy"))
            vegetables.add(ShopDetail("Pasumai Vegetable Shop","","Trichy"))
            vegetables.add(ShopDetail("FRESH & MORE Veg Shop","","Trichy"))

         /*Kumbakonam*/
            vegetables.add(ShopDetail("Kumbakonam Municipality Market","","Kumbakonam"))
            vegetables.add(ShopDetail("Uzhavan Pazhamudir Solai","","Kumbakonam"))
            vegetables.add(ShopDetail("Vedha KaiKarigal","","Kumbakonam"))
            vegetables.add(ShopDetail("Arul Jothi Vilas Market","","Kumbakonam"))
            vegetables.add(ShopDetail("Maruthu Vilas Vegetable","","Kumbakonam"))



            for(i in 0 until vegetables.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=vegetables.get(i).product_name
                shop.shopType="Vegetable Shops"
                shop.shopBgColor="#FFFFFF"
                shop.shopImageUrl=vegetables.get(i).product_image
                shop.location=vegetables.get(i).location
                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }

            /*Add Product items to respective shops*/
            addItemsToProduct()
        })

    }

    private fun setProfile()
    {
        AppExecutors.getInstance().diskIO().execute(Runnable {
            val userId = sharedPreferences?.getInt("userId",0)
            var user=AppDatabase.getInstance(GlobalClass.sInstance).userDao().getUser(userId!!)

AppExecutors.getInstance().mainThread().execute(Runnable {
    Glide.with(GlobalClass.getInstance()!!).asBitmap().load(user?.profileImage).placeholder(R.drawable.user_pic).error(R.drawable.user_pic).into(profileimage)
    name.text=user?.name
})
        })


    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            if (backPressedCount == 1) {
                finish()
            } else {
                backPressedCount++
                val toast = Toast.makeText(
                    applicationContext,
                    resources.getString(R.string.press_again),
                    Toast.LENGTH_SHORT
                )
                toast.show()
                object : Thread() {
                    override fun run() {
                        //super.run();
                        try {
                            sleep(3000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        } finally {
                            backPressedCount = 0
                        }
                    }
                }.start()
            }
        } else {
            super.onBackPressed()
        }

    }

    fun addItemsToProduct() {
        /*Grocery*/
        AppExecutors.getInstance().diskIO().execute(Runnable {
            var shops = AppDatabase.getInstance(this).shopsDao().loadAllByShop("Grocery Shops")
            if (shops!!.size > 0) {
                for (i in 0 until shops.size) {
                    var groceryProducts = ArrayList<Product>()

                    var shop = shops[i]
                    groceryProducts.add(
                        Product(
                            "Rice",
                            "45.00",
                            "1",
                            "Food rice",
                            "Grocery Shops",
                            shop.shopName
                        )
                    )
                    groceryProducts.add(
                        Product(
                            "Maida",
                            "30.00",
                            "1",
                            "All Flour",
                            "Grocery Shops",
                            shop.shopName
                        )
                    )
                    groceryProducts.add(
                        Product(
                            "Toor Dhal",
                            "20.00",
                            "1",
                            "Grams",
                            "Grocery Shops",
                            shop.shopName
                        )
                    )
                    groceryProducts.add(
                        Product(
                            "Sugar",
                            "45.00",
                            "1",
                            "White sugar",
                            "Grocery Shops",
                            shop.shopName
                        )
                    )
                    groceryProducts.add(
                        Product(
                            "Biscuit",
                            "20.00",
                            "1",
                            "Britania",
                            "Grocery Shops",
                            shop.shopName
                        )
                    )
                    for (i in 0 until groceryProducts.size) {
                        var product = groceryProducts.get(i)

                        AppDatabase.getInstance(this).productDao().insertProduct(product)

                    }
                }

            }

        })


        AppExecutors.getInstance().diskIO().execute(Runnable {
            var shops =
                AppDatabase.getInstance(this).shopsDao().loadAllByShop("Medicals")
            if (shops!!.size > 0) {
                for (i in 0 until shops.size) {
                    var shop = shops[i]

                    /*Medicals*/
                    var medicalProducts = ArrayList<Product>()
                    medicalProducts.add(
                        Product(
                            "Betnovate Oinment",
                            "60.00",
                            "1",
                            "Skin oinment",
                            "Medicals",
                            shop.shopName
                        )
                    )
                    medicalProducts.add(
                        Product(
                            "Paracetamol-350mg",
                            "3.00",
                            "1",
                            "Cold",
                            "Medicals",
                            shop.shopName
                        )
                    )
                    medicalProducts.add(
                        Product(
                            "Vicks Vapourb",
                            "20.00",
                            "1",
                            "Cold",
                            "Medicals",
                            shop.shopName
                        )
                    )
                    medicalProducts.add(
                        Product(
                            "CalPol-500mg",
                            "2.00",
                            "1",
                            "Fever",
                            "Medicals",
                            shop.shopName
                        )
                    )
                    medicalProducts.add(
                        Product(
                            "Anacin-150mg",
                            "5.00",
                            "1",
                            "Cold",
                            "Medicals",
                            shop.shopName
                        )
                    )
                    for (i in 0 until medicalProducts.size) {
                        var product = medicalProducts.get(i)

                        AppDatabase.getInstance(this).productDao().insertProduct(product)

                    }
                }
            }
        })


        AppExecutors.getInstance().diskIO().execute(Runnable {
            var shops =
                AppDatabase.getInstance(this).shopsDao().loadAllByShop("Saloon")
            if (shops!!.size > 0) {
                for (i in 0 until shops.size) {
                    var shop = shops[i]

                    /*Saloon*/
                    var saloonPersons = ArrayList<Product>()
                    saloonPersons.add(
                        Product(
                            "Akash",
                            "",
                            "1",
                            "Available time slot- 2-4",
                            "Saloon",
                            shop.shopName
                        )
                    )
                    saloonPersons.add(
                        Product(
                            "Shiva",
                            "",
                            "1",
                            "Available time slot- 4-6",
                            "Saloon",
                            shop.shopName
                        )
                    )
                    saloonPersons.add(
                        Product(
                            "Jeyaraj",
                            "",
                            "1",
                            "Available time slot- 9-11",
                            "Saloon",
                            shop.shopName
                        )
                    )
                    for (i in 0 until saloonPersons.size) {
                        var product = saloonPersons.get(i)

                        AppDatabase.getInstance(this).productDao().insertProduct(product)

                    }
                }
            }
        })



        AppExecutors.getInstance().diskIO().execute(Runnable {
            var shops =
                AppDatabase.getInstance(this).shopsDao().loadAllByShop("Physiotherapist")
            if (shops!!.size > 0) {
                for (i in 0 until shops.size) {
                    var shop = shops[i]

                    /*Physiotherapist*/
                    var physioteraphyPersons = ArrayList<Product>()
                    physioteraphyPersons.add(
                        Product(
                            "Dr.AnbuRaj",
                            "",
                            "1",
                            "Available time slot- 2-4",
                            "Physiotherapist",
                            shop.shopName
                        )
                    )
                    physioteraphyPersons.add(
                        Product(
                            "Dr.Raja",
                            "",
                            "1",
                            "Available time slot- 4-6",
                            "Physiotherapist",
                            shop.shopName
                        )
                    )
                    physioteraphyPersons.add(
                        Product(
                            "Dr.Maran",
                            "",
                            "1",
                            "Available time slot- 9-11",
                            "Physiotherapist",
                            shop.shopName
                        )
                    )
                    for (i in 0 until physioteraphyPersons.size) {
                        var product = physioteraphyPersons.get(i)

                        AppDatabase.getInstance(this).productDao().insertProduct(product)

                    }
                }
            }
        })



        AppExecutors.getInstance().diskIO().execute(Runnable {
            var shops =
                AppDatabase.getInstance(this).shopsDao().loadAllByShop("Hotels")
            if (shops!!.size > 0) {
                for (i in 0 until shops.size) {
                    var shop = shops[i]

                    /*Hotels*/
                    var hotelItems = ArrayList<Product>()
                    hotelItems.add(
                        Product(
                            "Parotta",
                            "10.00",
                            "1",
                            "Parotta",
                            "Hotels",
                            shop.shopName
                        )
                    )
                    hotelItems.add(
                        Product(
                            "Dosa",
                            "25.00",
                            "1",
                            "Dosa with chutney",
                            "Hotels",
                            shop.shopName
                        )
                    )
                    hotelItems.add(
                        Product(
                            "Idli",
                            "8.00",
                            "1",
                            "Idli with chutney",
                            "Hotels",
                            shop.shopName
                        )
                    )
                    for (i in 0 until hotelItems.size) {
                        var product = hotelItems.get(i)
                        AppDatabase.getInstance(this).productDao().insertProduct(product)

                    }
                }
            }
        })


        AppExecutors.getInstance().diskIO().execute(Runnable {
            var shops =
                AppDatabase.getInstance(this).shopsDao().loadAllByShop("Vegetable Shops")
            if (shops!!.size > 0) {
                for (i in 0 until shops.size) {
                    var shop = shops[i]
                    /*Vegetables*/
                    var vegetablesItems = ArrayList<Product>()
                    vegetablesItems.add(
                        Product(
                            "Carrot-100g",
                            "10.00",
                            "1",
                            "Carrot with 100 grams",
                            "Vegetable Shops",
                            shop.shopName
                        )
                    )
                    vegetablesItems.add(
                        Product(
                            "Brinjal-100g",
                            "25.00",
                            "1",
                            "Brinjal with 100 grams",
                            "Vegetable Shops",
                            shop.shopName
                        )
                    )
                    vegetablesItems.add(
                        Product(
                            "Drumstick-1",
                            "8.00",
                            "1",
                            "1 Drumstick",
                            "Vegetable Shops",
                            shop.shopName
                        )
                    )
                    for (i in 0 until vegetablesItems.size) {
                        var product = vegetablesItems.get(i)
                        AppDatabase.getInstance(this).productDao().insertProduct(product)

                    }
                }
            }
        })
    }

    fun animateCartItem()
    {
        val set = AnimatorInflater.loadAnimator(
            this,
            R.animator.flip
        ) as AnimatorSet
        set.setTarget(cartcount)
        cartcount?.setText(GlobalClass.cartList.size.toString())
        set.start()
    }

    override fun onResume() {
        super.onResume()
        animateCartItem()
    }
}