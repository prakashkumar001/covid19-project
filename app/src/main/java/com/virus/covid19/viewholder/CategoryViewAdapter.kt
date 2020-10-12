package com.virus.covid19.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.virus.covid19.R
import com.virus.covid19.interfaces.CardClickListener
import com.virus.covid19.model.Category
import kotlinx.android.synthetic.main.category_list_item.view.*

class CategoryViewAdapter( val mContext: Context, private val categoryList: List<Category>?,private var cardClickListener: CardClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        categoryViewHolder.itemView.product.text=categoryList?.get(position)?.categoryName
        Glide.with(mContext).asBitmap().load(categoryList?.get(position)?.categoryImage).into(categoryViewHolder.itemView.image)


        categoryViewHolder.itemView.setOnClickListener(View.OnClickListener {
            cardClickListener.onCardClick(categoryList?.get(position)?.categoryName!!)
        })

    }
}
