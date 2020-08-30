package com.virus.covid19.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.virus.covid19.R
import com.virus.covid19.viewholder.CategoryViewAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class Home : Fragment() {
    var categoryList:ArrayList<String> = ArrayList()
    var categoryRecyclerAdapter:CategoryViewAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_home, container, false)
        getListOfCategory()
        categoryRecyclerAdapter= CategoryViewAdapter(activity!!,categoryList)
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
}