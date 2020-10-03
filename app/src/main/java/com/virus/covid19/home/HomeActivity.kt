package com.virus.covid19.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
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
import com.virus.covid19.payment.CartPage
import com.virus.covid19.textview.CustomTextView
import com.virus.covid19.viewholder.DrawerAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity() {
    var navigationList:ArrayList<DrawerItem> = ArrayList()
    var backPressedCount = 0
    var cartcount:CustomTextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        cartcount = findViewById(R.id.cartcount) as CustomTextView

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.setDrawerListener(toggle)
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
            var grocery=ArrayList<String>()
            grocery.add("Nilgris")
            grocery.add("Global")
            grocery.add("Nature Stores")
            grocery.add("Snack Paradise")
            grocery.add("Reliance Super Market")

            for(i in 0 until grocery.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=grocery.get(i)
                shop.shopType="Grocery Shops"
                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }


            var saloon=ArrayList<String>()
            saloon.add("Tony & Guy")
            saloon.add("Naturals")
            saloon.add("Green Trends")
            saloon.add("City Saloon")
            saloon.add("Scissors & Razors")
            saloon.add("Beauty Hair Saloon")

            for(i in 0 until saloon.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=saloon.get(i)
                shop.shopType="Saloon"
                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }




            var medicals=ArrayList<String>()
            medicals.add("Apollo Pharmacy")
            medicals.add("Public Pharmacy")
            medicals.add("Vista Pharmacy")
            medicals.add("Stanley's Pharmacy")
            medicals.add("Joseph Medicals")
            medicals.add("KMC Medicals")


            for(i in 0 until medicals.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=medicals.get(i)
                shop.shopType="Medicals"
                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }




            var hotel=ArrayList<String>()
            hotel.add("Saravana Bhavan")
            hotel.add("Sea Shell")
            hotel.add("Thalapakatti")
            hotel.add("Kannapa")
            hotel.add("Banna Leaf")
            hotel.add("KFC")
            hotel.add("Dominos")


            for(i in 0 until hotel.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=hotel.get(i)
                shop.shopType="Hotels"
                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }


            var physio=ArrayList<String>()
            hotel.add("SRM Hospital")
            hotel.add("SRV Physio")
            hotel.add("Physiotheraphy and card")

            for(i in 0 until physio.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=physio.get(i)
                shop.shopType="Physiotherapist"
                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }


            var vegetables=ArrayList<String>()
            vegetables.add("Garden Roots")
            vegetables.add("SRR Vegetable Market")
            vegetables.add("At your door Market")
            vegetables.add("Muruyan Vegetable Shop")
            vegetables.add("Diamond Vegetable Market")
            vegetables.add("AVC Vegetable Market")



            for(i in 0 until vegetables.size)
            {
                /*Add shops to table*/
                var shop=Shops()
                shop.shopName=vegetables.get(i)
                shop.shopType="Vegetable Shops"
                AppDatabase.getInstance(this).shopsDao().insertShop(shop)
            }

            /*Add Product items to respective shops*/
            addItemsToProduct()
        })

    }

    private fun setProfile()
    {
        AppExecutors.getInstance().diskIO().execute(Runnable {
            var users=AppDatabase.getInstance(GlobalClass.sInstance).userDao().loadAllUser()
            if(users?.size!!>0)
            {
                var user=users.get(0)
AppExecutors.getInstance().mainThread().execute(Runnable {
    Glide.with(GlobalClass.getInstance()!!).asBitmap().load(user?.profileImage).into(profileimage)
    name.text=user?.name
})
            }
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

    fun addItemsToProduct()
    {
        /*Grocery*/
        var groceryProducts=ArrayList<Product>()
        groceryProducts.add(Product("Rice","45.00","1","Food rice","Grocery Shops"))
        groceryProducts.add(Product("Maida","30.00","1","All Flour","Grocery Shops"))
        groceryProducts.add(Product("Toor Dhal","20.00","1","Grams","Grocery Shops"))
        groceryProducts.add(Product("Sugar","45.00","1","White sugar","Grocery Shops"))
        groceryProducts.add(Product("Biscuit","20.00","1","Britania","Grocery Shops"))
        for(i in 0 until groceryProducts.size)
        {
            var product=groceryProducts.get(i)

            AppDatabase.getInstance(this).productDao().insertProduct(product)

        }


        /*Medicals*/
        var medicalProducts=ArrayList<Product>()
        medicalProducts.add(Product("Betnovate Oinment","60.00","1","Skin oinment","Medicals"))
        medicalProducts.add(Product("Paracetamol-350mg","3.00","1","Cold","Medicals"))
        medicalProducts.add(Product("Vicks Vapourb","20.00","1","Cold","Medicals"))
        medicalProducts.add(Product("CalPol-500mg","2.00","1","Fever","Medicals"))
        medicalProducts.add(Product("Anacin-150mg","5.00","1","Cold","Medicals"))
        for(i in 0 until medicalProducts.size)
        {
            var product=medicalProducts.get(i)

            AppDatabase.getInstance(this).productDao().insertProduct(product)

        }

        /*Saloon*/
        var saloonPersons=ArrayList<Product>()
        saloonPersons.add(Product("Akash","","1","Available time slot- 2-4","Saloon"))
        saloonPersons.add(Product("Shiva","","1","Available time slot- 4-6","Saloon"))
        saloonPersons.add(Product("Jeyaraj","","1","Available time slot- 9-11","Saloon"))
        for(i in 0 until saloonPersons.size)
        {
            var product=saloonPersons.get(i)

            AppDatabase.getInstance(this).productDao().insertProduct(product)

        }


        /*Physiotherapist*/
        var physioteraphyPersons=ArrayList<Product>()
        physioteraphyPersons.add(Product("Dr.AnbuRaj","1000.00","1","Available time slot- 2-4","Physiotherapist"))
        physioteraphyPersons.add(Product("Dr.Raja","700.00","1","Available time slot- 4-6","Physiotherapist"))
        physioteraphyPersons.add(Product("Dr.Maran","500.00","1","Available time slot- 9-11","Physiotherapist"))
        for(i in 0 until physioteraphyPersons.size)
        {
            var product=physioteraphyPersons.get(i)

            AppDatabase.getInstance(this).productDao().insertProduct(product)

        }


        /*Hotels*/
        var hotelItems=ArrayList<Product>()
        hotelItems.add(Product("Parotta","10.00","1","Parotta","Hotels"))
        hotelItems.add(Product("Dosa","25.00","1","Dosa with chutney","Hotels"))
        hotelItems.add(Product("Idli","8.00","1","Idli with chutney","Hotels"))
        for(i in 0 until hotelItems.size)
        {
            var product=hotelItems.get(i)
            AppDatabase.getInstance(this).productDao().insertProduct(product)

        }

        /*Vegetables*/
        var vegetablesItems=ArrayList<Product>()
        vegetablesItems.add(Product("Carrot-100g","10.00","1","Carrot with 100 grams","Vegetable Shops"))
        vegetablesItems.add(Product("Brinjal-100g","25.00","1","Brinjal with 100 grams","Vegetable Shops"))
        vegetablesItems.add(Product("Drumstick-1","8.00","1","1 Drumstick","Vegetable Shops"))
        for(i in 0 until vegetablesItems.size)
        {
            var product=vegetablesItems.get(i)
            AppDatabase.getInstance(this).productDao().insertProduct(product)

        }
    }
}