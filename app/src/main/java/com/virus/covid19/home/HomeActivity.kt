package com.virus.covid19.home

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.virus.covid19.R
import com.virus.covid19.fragments.Home
import com.virus.covid19.model.DrawerItem
import com.virus.covid19.viewholder.DrawerAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity() {
    var navigationList:ArrayList<DrawerItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
        selectFirstItemAsDefault()


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
        navigationList.add(DrawerItem("Change Password",R.drawable.cart_icon))
        navigationList.add(DrawerItem("Log Out",R.drawable.cart_icon))



    }

}