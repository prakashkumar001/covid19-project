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
        navigationList.add(DrawerItem("Home",R.drawable.ic_home))
        navigationList.add(DrawerItem("My Profile",R.drawable.ic_profile))
        navigationList.add(DrawerItem("My Orders",R.drawable.ic_orders))
        navigationList.add(DrawerItem("Log Out",R.drawable.ic_logout))



    }

    private fun addDataIntoDB()
    {
        AppExecutors.getInstance().diskIO().execute(Runnable {
            var grocery=ArrayList<ShopDetail>()
            grocery.add(ShopDetail("Nilgris","#756238","all"))
            grocery.add(ShopDetail("Global","#EC8150","all"))
            grocery.add(ShopDetail("Nature Stores","#29442A","all"))
            grocery.add(ShopDetail("Snack Paradise","#334F26","all"))
            grocery.add(ShopDetail("Reliance Super Market","#93F094","all"))

            /*Trichy*/
            grocery.add(ShopDetail("Reliance Digital Mart","#760B0C","Trichy"))
            grocery.add(ShopDetail("Roshan Maligai","#F10E3A","Trichy"))
            grocery.add(ShopDetail("Padma Mart","#B25164","Trichy"))
            grocery.add(ShopDetail("Saraswathi Stores","#D593A0","Trichy"))
            grocery.add(ShopDetail("Kaveri Super Market","#756238","Trichy"))


            /*Kumbakonam*/
            grocery.add(ShopDetail("City Super Maligai","#4C7538","Kumbakonam"))
            grocery.add(ShopDetail("Alangar Maligai","#35A95C","Kumbakonam"))
            grocery.add(ShopDetail("Greenz Shopping Mart","#9EA935","Kumbakonam"))
            grocery.add(ShopDetail("Sangam Maligai","#535828","Kumbakonam"))
            grocery.add(ShopDetail("Patanjali Arogya Kendra","#334F26","Kumbakonam"))

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
            saloon.add(ShopDetail("Tony & Guy","#EC8150","all"))
            saloon.add(ShopDetail("Naturals","#DD8E8E","all"))
            saloon.add(ShopDetail("Green Trends","#93F094","all"))
            saloon.add(ShopDetail("City Saloon","#2C682D","all"))
            saloon.add(ShopDetail("Scissors & Razors","#29442A","all"))
            saloon.add(ShopDetail("Beauty Hair Saloon","#C1DFC2","all"))

            /*Kumbakonam*/
            saloon.add(ShopDetail("Bala Saloon Shop","#EC8150","Kumbakonam"))
            saloon.add(ShopDetail("Mayuio Ladies Beauty Parlour","#DD8E8E","Kumbakonam"))
            saloon.add(ShopDetail("Sri Saloon For Men","#93F094","Kumbakonam"))
            saloon.add(ShopDetail("Kalai Beauty Parlour","","Kumbakonam"))
            saloon.add(ShopDetail("Priya Beauty Herbal Plus","#EC8150","Kumbakonam"))

            /*Trichy*/
            saloon.add(ShopDetail("Zazzle Men Saloon","#2C682D","Trichy"))
            saloon.add(ShopDetail("Golden Green Saloon","#DD8E8E","Trichy"))
            saloon.add(ShopDetail("ADM Saloon","#93F094","Trichy"))
            saloon.add(ShopDetail("Gold Stone Saloon","#2C682D","Trichy"))
            saloon.add(ShopDetail("MuthuKumar Saloon","#EC8150","Trichy"))



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
            medicals.add(ShopDetail("Apollo Pharmacy","#A0DCA2","all"))
            medicals.add(ShopDetail("Public Pharmacy","#A0A5DC","all"))
            medicals.add(ShopDetail("Vista Pharmacy","#5E8748","all"))
            medicals.add(ShopDetail("Stanley's Pharmacy","#785747","all"))
            medicals.add(ShopDetail("Joseph Medicals","#754732","all"))
            medicals.add(ShopDetail("KMC Medicals","#323775","all"))

            /*Trichy*/
            medicals.add(ShopDetail("Apollo Medicals","#A0A5DC","Trichy"))
            medicals.add(ShopDetail("Lakshmi Medicals","#785747","Trichy"))
            medicals.add(ShopDetail("Trichy Homeo Medicals","#5E8748","Trichy"))
            medicals.add(ShopDetail("MedPlus Medicals","#754732","Trichy"))
            medicals.add(ShopDetail("AVC Medicals & Generals","#323775","Trichy"))

            /*Kumbakonam*/
            medicals.add(ShopDetail("Kurunji Medicals","#5E8748","Kumbakonam"))
            medicals.add(ShopDetail("Bawa General Medicines","#754732","Kumbakonam"))
            medicals.add(ShopDetail("Amman Medicals","#785747","Kumbakonam"))
            medicals.add(ShopDetail("Cauvery MediPlus","#A0A5DC","Kumbakonam"))
            medicals.add(ShopDetail("Annai Siddha Medicals","#323775","Kumbakonam"))


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
            hotel.add(ShopDetail("Saravana Bhavan","#5E8748","all"))
            hotel.add(ShopDetail("Sea Shell","#8379D4","all"))
            hotel.add(ShopDetail("Thalapakatti","#D62D2D","all"))
            hotel.add(ShopDetail("Kannapa","#A0A5DC","all"))
            hotel.add(ShopDetail("Banna Leaf","#1D1468","all"))
            hotel.add(ShopDetail("KFC","#CE0707","all"))
            hotel.add(ShopDetail("Dominos","#758A0A","all"))

            /*Kumbakonam*/
            hotel.add(ShopDetail("Satharas Restaurent","#5E8748","Kumbakonam"))
            hotel.add(ShopDetail("Karakudi Chettinadu Restaurent","#8379D4","Kumbakonam"))
            hotel.add(ShopDetail("Sharma Hotel (Veg & Non-veg)","#A0A5DC","Kumbakonam"))
            hotel.add(ShopDetail("Alif Tandoori Restaurent","#D62D2D","Kumbakonam"))
            hotel.add(ShopDetail("Murali Cafe","#1D1468","Kumbakonam"))
            hotel.add(ShopDetail("Masala Cafe","#758A0A","Kumbakonam"))

            /*Trichy*/
            hotel.add(ShopDetail("Kannapa Hotel","#5E8748","Trichy"))
            hotel.add(ShopDetail("Saraswathi Cafe (Veg)","#758A0A","Trichy"))
            hotel.add(ShopDetail("Sangeetha Restaurent","#8379D4","Trichy"))
            hotel.add(ShopDetail("Ezham Suvai Hotel","#A0A5DC","Trichy"))
            hotel.add(ShopDetail("Murugan Idli Shop","#1D1468","Trichy"))


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
            physio.add(ShopDetail("SRM Hospital","#0DAB8C","all"))
            physio.add(ShopDetail("SRV Physio","#546308","all"))
            physio.add(ShopDetail("Physiotheraphy and card","#086351","all"))

            /*Trichy*/
            physio.add(ShopDetail("Jeevam Physiotheraphy","#546308","Trichy"))
            physio.add(ShopDetail("Kerala Kottakal Physiotheraphy","#4E5B0C","Trichy"))
            physio.add(ShopDetail("CARE Physiotheraphist Center","#9D622A","Trichy"))
            physio.add(ShopDetail("Mary Madha Physiotheraphy","#086351","Trichy"))
            physio.add(ShopDetail("Ramya Clinic","#0DAB8C","Trichy"))

            /*Kumbakonam*/
            physio.add(ShopDetail("Physio-Care Clinic","#086351","Kumbakonam"))
            physio.add(ShopDetail("Sri Vinayaga Physio Clinic","#546308","Kumbakonam"))
            physio.add(ShopDetail("Dr.ArulSelvi Clinic","#0DAB8C","Kumbakonam"))
            physio.add(ShopDetail("Q-Spine Physio Clinic","#4E5B0C","Kumbakonam"))
            physio.add(ShopDetail("Swathi Lab & Physiotheraphy","#9D622A","Kumbakonam"))


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
            vegetables.add(ShopDetail("Garden Roots","#7C9D2A","all"))
            vegetables.add(ShopDetail("SRR Vegetable Market","#0A738D","all"))
            vegetables.add(ShopDetail("At your door Market","#1C488C","all"))
            vegetables.add(ShopDetail("Muruyan Vegetable Shop","#760A74","all"))
            vegetables.add(ShopDetail("Diamond Vegetable Market","#46225F","all"))
            vegetables.add(ShopDetail("AVC Vegetable Market","#760A4A","all"))

          /*Trichy*/
            vegetables.add(ShopDetail("Padma Pazhamudir","#0A738D","Trichy"))
            vegetables.add(ShopDetail("Kumar Mandi","#760A4A","Trichy"))
            vegetables.add(ShopDetail("Srinivasa Vegetable Shop","#760A74","Trichy"))
            vegetables.add(ShopDetail("Pasumai Vegetable Shop","#7C9D2A","Trichy"))
            vegetables.add(ShopDetail("FRESH & MORE Veg Shop","#1C488C","Trichy"))

         /*Kumbakonam*/
            vegetables.add(ShopDetail("Kumbakonam Municipality Market","#8D1BDC","Kumbakonam"))
            vegetables.add(ShopDetail("Uzhavan Pazhamudir Solai","#46225F","Kumbakonam"))
            vegetables.add(ShopDetail("Vedha KaiKarigal","#7C9D2A","Kumbakonam"))
            vegetables.add(ShopDetail("Arul Jothi Vilas Market","#0A738D","Kumbakonam"))
            vegetables.add(ShopDetail("Maruthu Vilas Vegetable","#1C488C","Kumbakonam"))



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