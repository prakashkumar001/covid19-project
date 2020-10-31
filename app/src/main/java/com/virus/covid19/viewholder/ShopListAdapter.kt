package com.virus.covid19.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.virus.covid19.R
import com.virus.covid19.database.entities.Shops
import com.virus.covid19.interfaces.CardClickListener
import kotlinx.android.synthetic.main.drawer_item.view.icon
import kotlinx.android.synthetic.main.drawer_item.view.title

class ShopListAdapter(private val shopList:List<Shops>,private val cardClickListener: CardClickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mRecyclerViewHolder: RecyclerView.ViewHolder? = null
    private var mContext:Context?=null
    class ShopListViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_shop, viewGroup, false)
    ) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mRecyclerViewHolder= ShopListViewHolder(parent)
        mContext=parent.context

        return mRecyclerViewHolder as RecyclerView.ViewHolder
    }

    override fun getItemCount(): Int {

       return shopList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder=holder as ShopListViewHolder
        viewHolder.itemView.title.text=shopList.get(position).shopName
        Glide.with(mContext!!).asBitmap().load(shopList.get(position).shopImageUrl).placeholder(R.drawable.cart_image).error(R.drawable.cart_image).into(viewHolder.itemView.icon)

        viewHolder.itemView.setOnClickListener(View.OnClickListener {
            cardClickListener.getCategoryName(shopList.get(position).shopType!!)
            cardClickListener.onCardClick(shopList.get(position).shopName!!)
        })

    }
}