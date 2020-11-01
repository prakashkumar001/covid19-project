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
import com.virus.covid19.database.entities.OrderHistory
import com.virus.covid19.viewholder.OrderHistoryAdapter
import com.virus.covid19.viewholder.ShopListAdapter
import kotlinx.android.synthetic.main.fragment_shop_list.view.*

class MyOrders : Fragment() {
    private val sharedPrefFile = "Login"
    var sharedPreferences: SharedPreferences?=null
    var orderList:List<OrderHistory>?=ArrayList()
    var orderHistoryAdapter:OrderHistoryAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_myorders, container, false)
        sharedPreferences = activity?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        v.recyclerView.layoutManager= LinearLayoutManager(activity)
        v.recyclerView.addItemDecoration(
            DividerItemDecoration(
                v.recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        getOrderList(v)
        return v
    }

    private fun getOrderList(v:View)
    {
        AppExecutors.getInstance().diskIO().execute(Runnable {

            val userId = sharedPreferences?.getInt("userId",0)


                orderList = AppDatabase.getInstance(activity!!).orderHistoryDao().loadAllOrder(userId!!.toLong())
                AppExecutors.getInstance().mainThread().execute(Runnable {
                    orderHistoryAdapter= OrderHistoryAdapter(orderList!!)
                    v.recyclerView.adapter=orderHistoryAdapter
                    orderHistoryAdapter?.notifyDataSetChanged()

                })

        })
    }
}