package com.virus.covid19.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.virus.covid19.R
import com.virus.covid19.interfaces.CardClickListener
import com.virus.covid19.viewholder.CategoryViewAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

class Home : Fragment(), CardClickListener {
    var categoryList:ArrayList<String> = ArrayList()
    var categoryRecyclerAdapter:CategoryViewAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_home, container, false)
        getListOfCategory()
        categoryRecyclerAdapter= CategoryViewAdapter(activity!!,categoryList,this)
        v.recyclerView.layoutManager = GridLayoutManager(activity,2)
        v.recyclerView.adapter=categoryRecyclerAdapter
        return v
    }

    fun getListOfCategory()
    {
        categoryList.add("Grocery Shops")
        categoryList.add("Saloon")
        categoryList.add("Medicals")
        categoryList.add("Hotels")
        categoryList.add("Physiotherapist")
        categoryList.add("Vegetable Shops")


    }
    override fun onCardClick(shopName: String) {
        var shopListFragment=ShopListFragment()
        var bundle=Bundle()
        bundle.putString("Title",shopName)
        shopListFragment.setArguments(bundle)

       activity!!.supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left,
           R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
        .add(R.id.container, shopListFragment, shopListFragment.tag).addToBackStack(shopListFragment.tag).commit()
    }

    override fun getCategoryName(category: String) {

    }
}