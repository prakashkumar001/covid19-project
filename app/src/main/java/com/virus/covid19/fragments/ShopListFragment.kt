package com.virus.covid19.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.virus.covid19.R
import com.virus.covid19.database.AppDatabase
import com.virus.covid19.database.AppExecutors
import com.virus.covid19.database.entities.Shops
import com.virus.covid19.interfaces.CardClickListener
import com.virus.covid19.viewholder.ShopListAdapter
import kotlinx.android.synthetic.main.fragment_shop_list.view.*


class ShopListFragment : Fragment(),CardClickListener{
    var shopList:List<Shops>?=ArrayList()
    var shopListAdapter:ShopListAdapter?=null
    var shopName:String?=null
    var category:String?=null
    private val sharedPrefFile = "Login"
    var sharedPreferences: SharedPreferences?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_shop_list, container, false)
         sharedPreferences = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        shopName=arguments?.getString("Title")
        v.recyclerView.layoutManager=LinearLayoutManager(activity)
        v.recyclerView.addItemDecoration(
            DividerItemDecoration(
                v.recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        getShopList(v)
        return v
    }

    private fun getShopList(v:View)
    {
        AppExecutors.getInstance().diskIO().execute(Runnable {

            val userId = sharedPreferences?.getInt("userId",0)
            var user=AppDatabase.getInstance(activity!!).userDao().getUser(userId!!)

            if(user!=null){
                if(user.location.equals("Trichy",ignoreCase = true) || user.location.equals("Kumbakonam",ignoreCase = true)){

                }else{
                    user.location="all"

                }
                shopList = AppDatabase.getInstance(activity!!).shopsDao().loadAllByShop(shopName!!,user.location!!)
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    shopListAdapter=ShopListAdapter(shopList!!,this)
                    v.recyclerView.adapter=shopListAdapter
                    shopListAdapter?.notifyDataSetChanged()

                })
            }

        })
    }

    override fun onCardClick(shopName: String) {
        var shopFragment=ShopFragment()
        var bundle=Bundle()
        bundle.putString("Title",shopName)
        bundle.putString("Category",category)
        shopFragment.setArguments(bundle)
        activity!!.supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_left,
                R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
            .add(R.id.container, shopFragment, shopFragment.tag).addToBackStack(shopFragment.tag).commit()
    }

    override fun getCategoryName(category: String) {
        this.category=category
    }
}