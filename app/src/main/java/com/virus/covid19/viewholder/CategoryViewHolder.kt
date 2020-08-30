package com.virus.covid19.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.virus.covid19.R
import kotlinx.android.synthetic.main.category_list_item.view.*

class CategoryViewAdapter( val mContext: Context, private val categoryList: List<String>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mRecyclerViewHolder: RecyclerView.ViewHolder? = null

    class CategoryViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.category_list_item, viewGroup, false)
    ) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mRecyclerViewHolder=CategoryViewHolder(parent)

        return mRecyclerViewHolder as RecyclerView.ViewHolder

    }

    override fun getItemCount(): Int {

        return categoryList!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var categoryViewHolder=holder as CategoryViewHolder
        categoryViewHolder.itemView.product.text=categoryList?.get(position)

    }
}
